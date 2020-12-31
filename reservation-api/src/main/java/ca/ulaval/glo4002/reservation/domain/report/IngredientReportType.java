package ca.ulaval.glo4002.reservation.domain.report;

import ca.ulaval.glo4002.reservation.domain.report.exception.InvalidReportTypeException;

public enum IngredientReportType {
  UNIT("unit"), TOTAL("total");

  private final String reportType;

  IngredientReportType(String reportType) {
    this.reportType = reportType;
  }

  public static IngredientReportType valueOfName(String name) {
    for (IngredientReportType ingredientReportType : IngredientReportType.values()) {
      if (ingredientReportType.toString().equals(name)) {
        return ingredientReportType;
      }
    }
    throw new InvalidReportTypeException();
  }

  @Override
  public String toString() {
    return reportType;
  }
}
