package ca.ulaval.glo4002.reservation.domain.util;

import java.math.BigDecimal;
import java.util.Map;

public class MapUtil {

  public static <K> Map<K, BigDecimal> merge(Map<K, BigDecimal> firstMap,
                                             Map<K, BigDecimal> secondMap)
  {
    for (Map.Entry<K, BigDecimal> entry : secondMap.entrySet()) {
      if (firstMap.containsKey(entry.getKey())) {
        firstMap.put(entry.getKey(), firstMap.get(entry.getKey()).add(entry.getValue()));
      } else {
        firstMap.put(entry.getKey(), entry.getValue());
      }
    }
    return firstMap;
  }
}
