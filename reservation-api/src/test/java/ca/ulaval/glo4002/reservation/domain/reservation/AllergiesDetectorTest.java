package ca.ulaval.glo4002.reservation.domain.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.ingredient.ReservationIngredientCalculator;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.builder.CustomerBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.ReservationBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.TableBuilder;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

@ExtendWith(MockitoExtension.class)
class AllergiesDetectorTest {
  private static final RestrictionType ALLERGIES_RESTRICTION = RestrictionType.ALLERGIES;
  private static final RestrictionType VEGAN_RESTRICTION = RestrictionType.VEGAN;
  private static final RestrictionType NONE_RESTRICTION = RestrictionType.NONE;
  private static final IngredientName CARROTS_INGREDIENT = IngredientName.CARROTS;
  private static final LocalDateTime A_DATE_TIME = LocalDateTime.of(2050, 7, 20, 9, 33, 20, 0);

  @Mock
  private ReservationIngredientCalculator reservationIngredientCalculator;

  private AllergiesDetector allergiesDetector;

  @BeforeEach
  public void setUp() {
    allergiesDetector = new AllergiesDetector(reservationIngredientCalculator);
  }

  @Test
  public void givenReservationWithoutAllergies_whenCheckingIfAllergicFriendly_thenReservationIsAllowed() {
    // given
    Reservation reservation = givenReservationWithoutAllergicCustomer();
    List<Reservation> existingReservations = givenNoPriorReservations();
    Map<IngredientName, BigDecimal> dailyIngredients = givenNoCarrotsInReservationDayMenu();

    // when
    boolean isReservationAllowed = allergiesDetector.isReservationAllergicFriendly(reservation,
                                                                                   existingReservations,
                                                                                   dailyIngredients);

    // then
    assertThat(isReservationAllowed).isTrue();
  }

  @Test
  public void givenAllergiesAndCarrotsInReservationDayMenu_whenCheckingIfAllergicFriendly_thenReservationIsForbidden() {
    // given
    Reservation reservation = givenReservationWithAllergicCustomer();
    List<Reservation> existingReservations = Collections.singletonList(reservation);
    Map<IngredientName, BigDecimal> dailyIngredients = givenCarrotInReservationDayMenu();

    // when
    boolean isReservationAllowed = allergiesDetector.isReservationAllergicFriendly(reservation,
                                                                                   existingReservations,
                                                                                   dailyIngredients);

    // then
    assertThat(isReservationAllowed).isFalse();
  }

  @Test
  public void givenAllergiesAndNoCarrotsInReservationDayMenu_whenCheckingIfAllergicFriendly_thenReservationIsAllowed() {
    // given
    Reservation reservation = givenReservationWithAllergicCustomer();
    List<Reservation> existingReservations = Collections.singletonList(reservation);
    Map<IngredientName, BigDecimal> dailyIngredients = givenNoCarrotsInReservationDayMenu();

    // when
    boolean isReservationAllowed = allergiesDetector.isReservationAllergicFriendly(reservation,
                                                                                   existingReservations,
                                                                                   dailyIngredients);

    // then
    assertThat(isReservationAllowed).isTrue();
  }

  @Test
  public void givenReservationWithoutCarrots_whenCheckingIfAllergicFriendly_thenReservationIsAllowed() {
    // given
    Reservation reservation = givenReservationWithoutAllergicCustomer();
    Map<IngredientName, BigDecimal> ingredientQuantity = givenIngredientQuantity(IngredientName.BACON);
    given(reservationIngredientCalculator.getReservationIngredientsQuantity(reservation)).willReturn(ingredientQuantity);
    List<Reservation> existingReservations = Collections.singletonList(reservation);
    Map<IngredientName, BigDecimal> dailyIngredients = givenNoCarrotsInReservationDayMenu();

    // when
    boolean isReservationAllowed = allergiesDetector.isReservationAllergicFriendly(reservation,
                                                                                   existingReservations,
                                                                                   dailyIngredients);

    // then
    assertThat(isReservationAllowed).isTrue();
  }

  @Test
  public void givenCarrotsAndNoAllergiesInReservationDate_whenCheckingIfAllergicFriendly_thenReservationIsAllowed() {
    // given
    Reservation reservation = givenReservationWithCarrots();
    Map<IngredientName, BigDecimal> ingredientQuantity = givenIngredientQuantity(CARROTS_INGREDIENT);
    given(reservationIngredientCalculator.getReservationIngredientsQuantity(reservation)).willReturn(ingredientQuantity);
    List<Reservation> existingReservations = Collections.singletonList(reservation);
    Map<IngredientName, BigDecimal> dailyIngredients = givenNoCarrotsInReservationDayMenu();

    // when
    boolean isReservationAllowed = allergiesDetector.isReservationAllergicFriendly(reservation,
                                                                                   existingReservations,
                                                                                   dailyIngredients);

    // then
    assertThat(isReservationAllowed).isTrue();
  }

  @Test
  public void givenCarrotsAndAllergiesInReservationDate_whenCheckingIfAllergicFriendly_thenReservationIsForbidden() {
    // given
    Reservation reservation = givenReservationWithCarrots();
    Map<IngredientName, BigDecimal> ingredientQuantity = givenIngredientQuantity(CARROTS_INGREDIENT);
    given(reservationIngredientCalculator.getReservationIngredientsQuantity(reservation)).willReturn(ingredientQuantity);
    List<Reservation> existingReservations = createReservationRepositoryWithAllergies();

    // when
    boolean isReservationAllowed = allergiesDetector.isReservationAllergicFriendly(reservation,
                                                                                   existingReservations,
                                                                                   ingredientQuantity);

    // then
    assertThat(isReservationAllowed).isFalse();
  }

  @Test
  public void givenReservationWithAllergicCustomerAndCarrots_whenCheckingIfAllergicFriendly_thenReservationIsForbidden() {
    // given
    Reservation reservation = givenReservationWithAllergicCustomerAndCarrots();
    Map<IngredientName, BigDecimal> menuWithCarrots = Collections.singletonMap(CARROTS_INGREDIENT,
                                                                               BigDecimal.valueOf(1.0));
    List<Reservation> existingReservations = givenNoPriorReservations();

    // when
    boolean isReservationAllowed = allergiesDetector.isReservationAllergicFriendly(reservation,
                                                                                   existingReservations,
                                                                                   menuWithCarrots);

    // then
    assertThat(isReservationAllowed).isFalse();
  }

  private Reservation givenReservationWithoutAllergicCustomer() {
    Customer nonAllergicCustomer = new CustomerBuilder().withRestriction(VEGAN_RESTRICTION).build();
    Table table = new TableBuilder().withCustomer(nonAllergicCustomer).build();
    return new ReservationBuilder().withTable(table).withDinnerDate(A_DATE_TIME).build();
  }

  private Reservation givenReservationWithAllergicCustomer() {
    Customer allergicCustomer = new CustomerBuilder().withRestriction(ALLERGIES_RESTRICTION)
                                                     .build();
    Table table = new TableBuilder().withCustomer(allergicCustomer).build();
    return new ReservationBuilder().withTable(table).withDinnerDate(A_DATE_TIME).build();
  }

  private Reservation givenReservationWithCarrots() {
    Customer noneRestrictionCustomer = new CustomerBuilder().withRestriction(NONE_RESTRICTION)
                                                            .build();
    Table table = new TableBuilder().withCustomer(noneRestrictionCustomer).build();
    return new ReservationBuilder().withTable(table).withDinnerDate(A_DATE_TIME).build();
  }

  private Reservation givenReservationWithAllergicCustomerAndCarrots() {
    Customer carrotCustomer = new CustomerBuilder().withRestriction(NONE_RESTRICTION).build();
    Customer allergicCustomer = new CustomerBuilder().withRestriction(ALLERGIES_RESTRICTION)
                                                     .build();
    Table table = new TableBuilder().withCustomer(allergicCustomer)
                                    .withCustomer(carrotCustomer)
                                    .build();
    return new ReservationBuilder().withTable(table).withDinnerDate(A_DATE_TIME).build();
  }

  private List<Reservation> createReservationRepositoryWithAllergies() {
    Customer customer = new CustomerBuilder().withRestriction(ALLERGIES_RESTRICTION).build();
    Table table = new TableBuilder().withCustomer(customer).build();
    Reservation reservation = new ReservationBuilder().withTable(table)
                                                      .withDinnerDate(AllergiesDetectorTest.A_DATE_TIME)
                                                      .build();
    return Collections.singletonList(reservation);
  }

  private Map<IngredientName, BigDecimal> givenIngredientQuantity(IngredientName ingredient) {
    return Map.of(ingredient, BigDecimal.valueOf(2.0));
  }

  private List<Reservation> givenNoPriorReservations() {
    return new ArrayList<>();
  }

  private Map<IngredientName, BigDecimal> givenNoCarrotsInReservationDayMenu() {
    return new HashMap<>();
  }

  private Map<IngredientName, BigDecimal> givenCarrotInReservationDayMenu() {
    return Map.of(IngredientName.CARROTS, BigDecimal.ONE);
  }
}