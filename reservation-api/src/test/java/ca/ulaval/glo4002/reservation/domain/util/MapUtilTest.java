package ca.ulaval.glo4002.reservation.domain.util;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class MapUtilTest {

  public static final String FIRST_KEY = "key";
  public static final String SECOND_KEY = "second key";
  public static final String THIRD_KEY = "third key";

  @Test
  public void givenTwoMaps_whenMerge_thenReturnMergedMap() {
    // given
    Map<String, BigDecimal> firstMap = new HashMap<>();
    firstMap.put(FIRST_KEY, BigDecimal.valueOf(2.0));
    firstMap.put(SECOND_KEY, BigDecimal.valueOf(1.0));
    Map<String, BigDecimal> secondMap = new HashMap<>();
    secondMap.put(THIRD_KEY, BigDecimal.valueOf(1.0));

    // when
    Map<String, BigDecimal> resultingMap = MapUtil.merge(firstMap, secondMap);

    // then
    assertThat(resultingMap).containsEntry(FIRST_KEY, BigDecimal.valueOf(2.0));
    assertThat(resultingMap).containsEntry(SECOND_KEY, BigDecimal.valueOf(1.0));
    assertThat(resultingMap).containsEntry(THIRD_KEY, BigDecimal.valueOf(1.0));
  }

  @Test
  public void givenTwoMapsWithSameKey_whenMerge_thenAddValuesWithSameKey() {
    // given
    Map<String, BigDecimal> firstMap = new HashMap<>();
    firstMap.put(FIRST_KEY, BigDecimal.valueOf(2.0));
    firstMap.put(SECOND_KEY, BigDecimal.valueOf(1.0));
    Map<String, BigDecimal> secondMap = new HashMap<>();
    secondMap.put(FIRST_KEY, BigDecimal.valueOf(1.0));

    // when
    Map<String, BigDecimal> resultingMap = MapUtil.merge(firstMap, secondMap);

    // then
    assertThat(resultingMap).containsEntry(FIRST_KEY, BigDecimal.valueOf(3.0));
    assertThat(resultingMap).containsEntry(SECOND_KEY, BigDecimal.valueOf(1.0));
  }
}
