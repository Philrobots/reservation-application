package ca.ulaval.glo4002.reservation.api.report.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChefReportDto {

  @JsonProperty("dates")
  private final List<ChefReportInformationDto> chefsReportInformationDto;

  public ChefReportDto(List<ChefReportInformationDto> chefsReportInformationDto) {
    this.chefsReportInformationDto = chefsReportInformationDto;
  }

  public List<ChefReportInformationDto> getChefsReportInformationDto() {
    return chefsReportInformationDto;
  }
}
