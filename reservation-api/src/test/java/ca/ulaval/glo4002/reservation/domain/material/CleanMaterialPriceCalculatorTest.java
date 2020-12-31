package ca.ulaval.glo4002.reservation.domain.material;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CleanMaterialPriceCalculatorTest {
  private static final BigDecimal SOME_MATERIAL_QUANTITY = BigDecimal.valueOf(3);
  private static final BigDecimal EXPECTED_PRICE_BASE_CUSTOMER = BigDecimal.valueOf(200);
  private static final BigDecimal EXPECTED_EMPTY_PRICE = BigDecimal.ZERO;

  private CleanMaterialPriceCalculator cleanMaterialPriceCalculator;

  @BeforeEach
  public void setUp() {
    cleanMaterialPriceCalculator = new CleanMaterialPriceCalculator();
  }

  @Test
  public void whenGetTotalPrice_thenPriceIsCorrect() {
    // given
    Map<Material, BigDecimal> cleanedDishes = givenDishesQuantity();

    // when
    BigDecimal cleanPrice = cleanMaterialPriceCalculator.calculateCleaningPrice(cleanedDishes);

    // then
    assertThat(cleanPrice).isEquivalentAccordingToCompareTo(EXPECTED_PRICE_BASE_CUSTOMER);
  }

  @Test
  public void givenNoDishesToBuy_whenGetTotalPrice_thenPriceIsZero() {
    // given
    Map<Material, BigDecimal> cleanedDishes = givenEmptyDishes();

    // when
    BigDecimal cleanPrice = cleanMaterialPriceCalculator.calculateCleaningPrice(cleanedDishes);

    // then
    assertThat(cleanPrice).isEquivalentAccordingToCompareTo(EXPECTED_EMPTY_PRICE);
  }

  private Map<Material, BigDecimal> givenDishesQuantity() {
    return Map.of(Material.FORK,
                  SOME_MATERIAL_QUANTITY,
                  Material.BOWL,
                  SOME_MATERIAL_QUANTITY,
                  Material.KNIFE,
                  SOME_MATERIAL_QUANTITY,
                  Material.PLATE,
                  SOME_MATERIAL_QUANTITY,
                  Material.SPOON,
                  SOME_MATERIAL_QUANTITY);
  }

  private HashMap<Material, BigDecimal> givenEmptyDishes() {
    return new HashMap<>();
  }
}
