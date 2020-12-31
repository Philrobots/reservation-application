package ca.ulaval.glo4002.reservation.domain.material;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DailyDishesQuantityTest {
  private static final int ONE_CUSTOMER = 1;
  private static final int TWO_CUSTOMERS = 2;
  private static final int ZERO_RESTRICTION = 0;
  private static final int ONE_RESTRICTION = 1;
  private static final BigDecimal BASE_NUMBER_OF_DISHES_FOR_ONE_CUSTOMER = BigDecimal.valueOf(3);
  private static final BigDecimal BASE_NUMBER_OF_DISHES_FOR_TWO_CUSTOMERS = BigDecimal.valueOf(6);
  private static final BigDecimal NUMBER_OF_FORKS_AND_PLATES_FOR_ONE_RESTRICTION_AND_ONE_CUSTOMER = BigDecimal.valueOf(4);
  private static final BigDecimal NUMBER_OF_FORK_AND_PLATE_FOR_ONE_RESTRICTION_AND_TWO_CUSTOMERS = BigDecimal.valueOf(7);

  private DailyDishesQuantity dailyDishesQuantity;

  @BeforeEach
  public void setUpDailyDishesQuantity() {
    dailyDishesQuantity = new DailyDishesQuantity();
  }

  @Test
  public void whenUpdateQuantityWithOneCustomerAndZeroRestriction_thenTheRightAmountOfDishesIsAddedForOneCustomer() {
    // given
    Map<Material, BigDecimal> expectedDishesQuantity = givenExpectedMaterialQuantityForOneCustomer();

    // when
    dailyDishesQuantity.updateQuantity(ONE_CUSTOMER, ZERO_RESTRICTION);

    // then
    assertThat(dailyDishesQuantity.getDishesQuantity()).isEqualTo(expectedDishesQuantity);
  }

  @Test
  public void whenUpdateQuantityWithManyCustomers_thenTotalAmountOfDishesNeededIsCalculatedForAllCustomers() {
    // given
    Map<Material, BigDecimal> expectedDishesQuantity = givenExpectedMaterialQuantityForTwoCustomers();

    // when
    dailyDishesQuantity.updateQuantity(TWO_CUSTOMERS, ZERO_RESTRICTION);

    // then
    assertThat(dailyDishesQuantity.getDishesQuantity()).isEqualTo(expectedDishesQuantity);
  }

  @Test
  public void givenOneExistingCustomer_whenUpdateQuantityWithOneCustomer_thenTotalAmountOfDishesNeededIsCalculatedForTwoCustomers() {
    // given
    Map<Material, BigDecimal> expectedDishesQuantity = givenExpectedMaterialQuantityForTwoCustomers();
    dailyDishesQuantity.updateQuantity(ONE_CUSTOMER, ZERO_RESTRICTION);

    // when
    dailyDishesQuantity.updateQuantity(ONE_CUSTOMER, ZERO_RESTRICTION);

    // then
    assertThat(dailyDishesQuantity.getDishesQuantity()).isEqualTo(expectedDishesQuantity);
  }

  @Test
  public void whenUpdateQuantityWithOneCustomerAndOneRestriction_thenOneExtraForkAndPlateAreAdded() {
    // given
    Map<Material, BigDecimal> expectedDishesQuantity = givenExpectedMaterialQuantityForOneCustomerAndOneRestriction();

    // when
    dailyDishesQuantity.updateQuantity(ONE_CUSTOMER, ONE_RESTRICTION);

    // then
    assertThat(dailyDishesQuantity.getDishesQuantity()).isEqualTo(expectedDishesQuantity);
  }

  @Test
  public void givenExistingDishesQuantity_whenUpdateQuantityWithOneCustomerAndOneRestriction_thenTotalDishesQuantityIsProperlyCalculated() {
    // given
    Map<Material, BigDecimal> expectedDishesQuantity = givenExpectedMaterialQuantityForTwoCustomersAndOneRestriction();
    dailyDishesQuantity.updateQuantity(ONE_CUSTOMER, ZERO_RESTRICTION);

    // when
    dailyDishesQuantity.updateQuantity(ONE_CUSTOMER, ONE_RESTRICTION);

    // then
    assertThat(dailyDishesQuantity.getDishesQuantity()).isEqualTo(expectedDishesQuantity);
  }

  @Test
  public void givenCustomerWithTwoRestrictions_whenUpdateQuantity_thenTotalDishesQuantityIsProperlyCalculated() {
    // given
    Map<Material, BigDecimal> expectedDishesQuantity = givenExpectedMaterialQuantityForOneCustomerWithTwoRestrictions();

    // when
    dailyDishesQuantity.updateQuantity(1, 2);

    // then
    assertThat(dailyDishesQuantity.getDishesQuantity()).isEqualTo(expectedDishesQuantity);

  }

  private Map<Material, BigDecimal> givenExpectedMaterialQuantityForOneCustomerWithTwoRestrictions() {
    Map<Material, BigDecimal> materialQuantity = new HashMap<>();
    materialQuantity.put(Material.SPOON, BigDecimal.valueOf(3));
    materialQuantity.put(Material.KNIFE, BigDecimal.valueOf(3));
    materialQuantity.put(Material.BOWL, BigDecimal.valueOf(3));
    materialQuantity.put(Material.FORK, BigDecimal.valueOf(5));
    materialQuantity.put(Material.PLATE, BigDecimal.valueOf(5));
    return materialQuantity;
  }

  private Map<Material, BigDecimal> givenExpectedMaterialQuantityForOneCustomer() {
    Map<Material, BigDecimal> materialQuantity = new HashMap<>();
    for (Material material : Material.values()) {
      materialQuantity.put(material, BASE_NUMBER_OF_DISHES_FOR_ONE_CUSTOMER);
    }
    return materialQuantity;
  }

  private Map<Material, BigDecimal> givenExpectedMaterialQuantityForOneCustomerAndOneRestriction() {
    Map<Material, BigDecimal> materialQuantity = new HashMap<>();
    materialQuantity.put(Material.SPOON, BASE_NUMBER_OF_DISHES_FOR_ONE_CUSTOMER);
    materialQuantity.put(Material.KNIFE, BASE_NUMBER_OF_DISHES_FOR_ONE_CUSTOMER);
    materialQuantity.put(Material.BOWL, BASE_NUMBER_OF_DISHES_FOR_ONE_CUSTOMER);
    materialQuantity.put(Material.FORK,
                         NUMBER_OF_FORKS_AND_PLATES_FOR_ONE_RESTRICTION_AND_ONE_CUSTOMER);
    materialQuantity.put(Material.PLATE,
                         NUMBER_OF_FORKS_AND_PLATES_FOR_ONE_RESTRICTION_AND_ONE_CUSTOMER);
    return materialQuantity;
  }

  private Map<Material, BigDecimal> givenExpectedMaterialQuantityForTwoCustomers() {
    Map<Material, BigDecimal> materialQuantity = new HashMap<>();
    for (Material material : Material.values()) {
      materialQuantity.put(material, BASE_NUMBER_OF_DISHES_FOR_TWO_CUSTOMERS);
    }
    return materialQuantity;
  }

  private Map<Material, BigDecimal> givenExpectedMaterialQuantityForTwoCustomersAndOneRestriction() {
    Map<Material, BigDecimal> materialQuantity = new HashMap<>();
    materialQuantity.put(Material.SPOON, BASE_NUMBER_OF_DISHES_FOR_TWO_CUSTOMERS);
    materialQuantity.put(Material.KNIFE, BASE_NUMBER_OF_DISHES_FOR_TWO_CUSTOMERS);
    materialQuantity.put(Material.BOWL, BASE_NUMBER_OF_DISHES_FOR_TWO_CUSTOMERS);
    materialQuantity.put(Material.FORK,
                         NUMBER_OF_FORK_AND_PLATE_FOR_ONE_RESTRICTION_AND_TWO_CUSTOMERS);
    materialQuantity.put(Material.PLATE,
                         NUMBER_OF_FORK_AND_PLATE_FOR_ONE_RESTRICTION_AND_TWO_CUSTOMERS);
    return materialQuantity;
  }
}
