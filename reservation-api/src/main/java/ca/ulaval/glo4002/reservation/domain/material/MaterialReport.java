package ca.ulaval.glo4002.reservation.domain.material;

import java.math.BigDecimal;
import java.util.List;

public class MaterialReport {
  private final List<MaterialReportInformation> allMaterialReportInformation;

  public MaterialReport(List<MaterialReportInformation> materialReportInformation) {
    allMaterialReportInformation = materialReportInformation;
  }

  public List<MaterialReportInformation> getMaterialReportInformation() {
    return allMaterialReportInformation;
  }

  public BigDecimal getMaterialCost() {
    BigDecimal totalCost = BigDecimal.ZERO;
    for (MaterialReportInformation materialReportInformation : allMaterialReportInformation) {
      totalCost = totalCost.add(materialReportInformation.getTotalPrice());
    }
    return totalCost;
  }

}
