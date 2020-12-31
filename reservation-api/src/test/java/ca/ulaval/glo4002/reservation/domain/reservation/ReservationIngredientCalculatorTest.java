package ca.ulaval.glo4002.reservation.domain.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
import ca.ulaval.glo4002.reservation.domain.fullcourse.MenuRepository;

@ExtendWith(MockitoExtension.class)
class ReservationIngredientCalculatorTest {

  private ReservationIngredientCalculator reservationIngredientCalculator;

  @Mock
  MenuRepository menuRepository;

  @BeforeEach
  public void setUp() {
    reservationIngredientCalculator = new ReservationIngredientCalculator(menuRepository);
  }

  @Test
  public void whenGetReservationIngredientsQuantity_thenReturnIngredientsQuantity() {
    // given
    Customer customer = new CustomerBuilder().withRestriction(RestrictionType.VEGAN).build();
    Table table = new TableBuilder().withCustomer(customer).build();
    Reservation reservation = new ReservationBuilder().withTable(table).build();
    Map<IngredientName, BigDecimal> veganMenu = givenVeganCourseIngredientsQuantity();
    given(menuRepository.getIngredientsQuantityByRestrictionType(RestrictionType.VEGAN)).willReturn(veganMenu);

    // when
    Map<IngredientName, BigDecimal> ingredientQuantity = reservationIngredientCalculator.getReservationIngredientsQuantity(reservation);

    // then
    assertThat(ingredientQuantity).isEqualTo(veganMenu);
  }

  private Map<IngredientName, BigDecimal> givenVeganCourseIngredientsQuantity() {
    Map<IngredientName, BigDecimal> ingredientDoubleMap = new HashMap<>();
    ingredientDoubleMap.put(IngredientName.TOMATO, BigDecimal.valueOf(5.0));
    ingredientDoubleMap.put(IngredientName.KIWI, BigDecimal.valueOf(8.0));
    ingredientDoubleMap.put(IngredientName.WORCESTERSHIRE_SAUCE, BigDecimal.valueOf(5.0));
    ingredientDoubleMap.put(IngredientName.KIMCHI, BigDecimal.valueOf(10.0));
    return ingredientDoubleMap;
  }
}
