package ca.ulaval.glo4002.reservation.api.report.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "dates" })
public class UnitReportDto {
  @JsonProperty("dates")
  private List<UnitReportDayDto> unitReportDayDtos;

  public UnitReportDto(List<UnitReportDayDto> unitReportDayDtos) {
    this.unitReportDayDtos = unitReportDayDtos;
  }

  public List<UnitReportDayDto> getUnitReportDayDtos() {
    return unitReportDayDtos;
  }
}
