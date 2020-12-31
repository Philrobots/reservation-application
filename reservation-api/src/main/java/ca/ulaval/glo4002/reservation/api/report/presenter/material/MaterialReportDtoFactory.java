package ca.ulaval.glo4002.reservation.api.report.presenter.material;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import ca.ulaval.glo4002.reservation.api.report.dto.MaterialReportDayDto;
import ca.ulaval.glo4002.reservation.api.report.dto.MaterialReportDto;
import ca.ulaval.glo4002.reservation.domain.material.Material;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReport;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReportInformation;

public class MaterialReportDtoFactory {
  public MaterialReportDto create(MaterialReport materialReport) {
    ArrayList<MaterialReportDayDto> dayDtos = new ArrayList<>();
    for (MaterialReportInformation materialReportInformation : materialReport.getMaterialReportInformation()) {
      BigDecimal price = materialReportInformation.getTotalPrice();
      Map<String, BigDecimal> boughtDishes = createStringMap(materialReportInformation.getBoughtDishes());
      Map<String, BigDecimal> cleanedDishes = createStringMap(materialReportInformation.getCleanedDishes());

      MaterialReportDayDto dayDto = new MaterialReportDayDto(materialReportInformation.getDate()
                                                                                      .toString(),
                                                             boughtDishes,
                                                             cleanedDishes,
                                                             price);
      dayDtos.add(dayDto);
    }
    dayDtos.sort(Comparator.comparing(MaterialReportDayDto::getDate));
    return new MaterialReportDto(dayDtos);
  }

  private Map<String, BigDecimal> createStringMap(Map<Material, BigDecimal> dishes) {
    TreeMap<String, BigDecimal> materialNameStringifyToQuantity = new TreeMap<>();

    BigDecimal sum = dishes.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    if (sum.compareTo(BigDecimal.ZERO) > 0) {
      for (Map.Entry<Material, BigDecimal> materialToQuantity : dishes.entrySet()) {

        materialNameStringifyToQuantity.put(materialToQuantity.getKey().getName(),
                                            materialToQuantity.getValue());
      }
    }
    return materialNameStringifyToQuantity;
  }
}