package ca.ulaval.glo4002.reservation.api.report.dto;

import java.util.List;

public class MaterialReportDto {
  private final List<MaterialReportDayDto> dates;

  public MaterialReportDto(List<MaterialReportDayDto> dates) {
    this.dates = dates;
  }

  public List<MaterialReportDayDto> getDates() {
    return dates;
  }
}
