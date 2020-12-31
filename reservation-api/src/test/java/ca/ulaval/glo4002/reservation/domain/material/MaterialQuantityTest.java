package ca.ulaval.glo4002.reservation.domain.material;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class MaterialQuantityTest {
  private final static Material A_MATERIAL = Material.BOWL;
  private final static BigDecimal A_QUANTITY = BigDecimal.valueOf(324.5214);
  private final static BigDecimal ANOTHER_QUANTITY = BigDecimal.valueOf(64);
  private final static BigDecimal EXPECTED_TOTAL_QUANTITY = BigDecimal.valueOf(388.5214);

  @Test
  public void givenMaterialAndQuantity_whenInitialized_thenMaterialQuantityIsCorrectlyConstructed() {
    // when
    MaterialQuantity materialQuantity = new MaterialQuantity(A_MATERIAL, A_QUANTITY);

    // then
    assertThat(materialQuantity.getMaterial()).isEqualTo(A_MATERIAL);
    assertThat(materialQuantity.getQuantity()).isEquivalentAccordingToCompareTo(A_QUANTITY);
  }

  @Test
  public void givenAQuantity_whenAddQuantity_thenQuantityIsAddedToTotalQuantity() {
    // given
    MaterialQuantity materialQuantity = new MaterialQuantity(A_MATERIAL, A_QUANTITY);

    // when
    materialQuantity.addQuantity(ANOTHER_QUANTITY);

    // then
    assertThat(materialQuantity.getQuantity()).isEquivalentAccordingToCompareTo(EXPECTED_TOTAL_QUANTITY);
  }
}
