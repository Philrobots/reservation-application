package ca.ulaval.glo4002.reservation.infra.inmemory;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.builder.CustomerBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.ReservationBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.TableBuilder;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.reservation.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryIngredientQuantityRepositoryTest {
  private static final LocalDateTime A_DINNER_DATE = LocalDateTime.of(2150, 7, 20, 3, 4);
  private static final LocalDate DINNER_START_DATE = LocalDate.of(2150, 7, 19);

  private InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository;

  private final Map<IngredientName, BigDecimal> reservationIngredientsQuantity = new HashMap<>();

  @BeforeEach
  public void setUp() {
    inMemoryIngredientQuantityRepository = new InMemoryIngredientQuantityRepository();
  }

  @Test
  public void whenInitialized_thenRepositoryIsEmpty() {
    // when
    InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository = new InMemoryIngredientQuantityRepository();

    // then
    assertThat(inMemoryIngredientQuantityRepository.isQuantityEmpty()).isTrue();
  }

  @Test
  public void givenADateWithIngredients_whenUpdateIngredientInformation_thenIngredientInformationShouldBeUpdated() {
    // when
    inMemoryIngredientQuantityRepository.updateIngredientsQuantity(reservationIngredientsQuantity, DINNER_START_DATE);

    // then
    assertThat(inMemoryIngredientQuantityRepository.isQuantityEmpty()).isFalse();
  }

  @Test
  public void givenEmptyRepository_whenGetIngredientInformation_thenReturnEmptyMap() {
    // when
    Map<IngredientName, BigDecimal> ingredientsQuantity = inMemoryIngredientQuantityRepository.getIngredientsQuantityByDate(LocalDate.from(A_DINNER_DATE));

    // then
    assertThat(ingredientsQuantity).isEmpty();
  }

  @Test
  public void givenExistingIngredientInformationAtDate_whenGetIngredientInformation_thenReturnIngredientInformationForDesiredDay() {
    // given
    Map<IngredientName, BigDecimal> expectedIngredientsQuantity = givenIngredientsQuantity();

    inMemoryIngredientQuantityRepository.updateIngredientsQuantity(expectedIngredientsQuantity, LocalDate.from(A_DINNER_DATE));

    // when
    Map<IngredientName, BigDecimal> ingredientsQuantity = inMemoryIngredientQuantityRepository.getIngredientsQuantityByDate(LocalDate.from(A_DINNER_DATE));

    // then
    assertThat(ingredientsQuantity).isEqualTo(expectedIngredientsQuantity);
  }

  @Test
  public void givenTwoOfTheSameIngredient_whenGetIngredientInformation_thenReturnIngredientInformationEquivalentToTwoReservation() {
    // given
    inMemoryIngredientQuantityRepository.updateIngredientsQuantity(givenIngredientsQuantity(), A_DINNER_DATE.toLocalDate());
    inMemoryIngredientQuantityRepository.updateIngredientsQuantity(givenIngredientsQuantity(), A_DINNER_DATE.toLocalDate());

    // when
    Map<IngredientName, BigDecimal> ingredientsQuantity = inMemoryIngredientQuantityRepository.getIngredientsQuantityByDate(LocalDate.from(A_DINNER_DATE));

    // then
    assertThat(ingredientsQuantity).isEqualTo(givenIngredientsQuantityEquivalentToTwoOfTheSameReservation());
  }

  @Test
  public void givenAReservation_whenUpdateIngredientInformation_thenIngredientAreCalculatedForAllCustomers() {
    // given
    Customer aCustomer = new CustomerBuilder().withRestriction(RestrictionType.VEGAN).build();
    Customer anotherCustomer = new CustomerBuilder().withRestriction(RestrictionType.ILLNESS)
                                                    .build();
    Table table = new TableBuilder().withCustomers(Arrays.asList(aCustomer, anotherCustomer))
                                    .build();
    Reservation reservation = new ReservationBuilder().withTable(table)
                                                      .withDinnerDate(A_DINNER_DATE)
                                                      .build();
    // when
    inMemoryIngredientQuantityRepository.updateIngredientsQuantity(givenIllnessAndVeganIngredientsQuantity(), reservation.getDinnerDate().toLocalDate());

    // then
    assertThat(inMemoryIngredientQuantityRepository.getIngredientsQuantityByDate(LocalDate.from(reservation.getDinnerDate()))).isEqualTo(givenIllnessAndVeganIngredientsQuantity());

  }

  private Map<IngredientName, BigDecimal> givenIllnessAndVeganIngredientsQuantity() {
    Map<IngredientName, BigDecimal> ingredientBigDecimalMap = new HashMap<>();
    ingredientBigDecimalMap.put(IngredientName.SCALLOPS, BigDecimal.valueOf(2.0));
    ingredientBigDecimalMap.put(IngredientName.BUTTERNUT_SQUASH, BigDecimal.valueOf(4.0));
    ingredientBigDecimalMap.put(IngredientName.KIWI, BigDecimal.valueOf(13.0));
    ingredientBigDecimalMap.put(IngredientName.PEPPERONI, BigDecimal.valueOf(2.0));
    ingredientBigDecimalMap.put(IngredientName.TOMATO, BigDecimal.valueOf(5.0));
    ingredientBigDecimalMap.put(IngredientName.WORCESTERSHIRE_SAUCE, BigDecimal.valueOf(5.0));
    ingredientBigDecimalMap.put(IngredientName.KIMCHI, BigDecimal.valueOf(10.0));
    return ingredientBigDecimalMap;
  }

  private Map<IngredientName, BigDecimal> givenIngredientsQuantity() {
    Map<IngredientName, BigDecimal> ingredientBigDecimalMap = new HashMap<>();
    ingredientBigDecimalMap.put(IngredientName.MARMALADE, BigDecimal.valueOf(5.0));
    ingredientBigDecimalMap.put(IngredientName.PLANTAIN, BigDecimal.valueOf(8.0));
    ingredientBigDecimalMap.put(IngredientName.BACON, BigDecimal.valueOf(5.0));
    ingredientBigDecimalMap.put(IngredientName.TOFU, BigDecimal.valueOf(10.0));
    return ingredientBigDecimalMap;
  }

  private Map<IngredientName, BigDecimal> givenIngredientsQuantityEquivalentToTwoOfTheSameReservation() {
    Map<IngredientName, BigDecimal> ingredientBigDecimalMap = new HashMap<>();
    ingredientBigDecimalMap.put(IngredientName.MARMALADE, BigDecimal.valueOf(10.0));
    ingredientBigDecimalMap.put(IngredientName.PLANTAIN, BigDecimal.valueOf(16.0));
    ingredientBigDecimalMap.put(IngredientName.BACON, BigDecimal.valueOf(10.0));
    ingredientBigDecimalMap.put(IngredientName.TOFU, BigDecimal.valueOf(20.0));
    return ingredientBigDecimalMap;
  }
}
