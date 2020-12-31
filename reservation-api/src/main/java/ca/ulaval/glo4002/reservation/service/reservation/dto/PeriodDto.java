package ca.ulaval.glo4002.reservation.service.reservation.dto;

public class PeriodDto {
  private final String startDate;
  private final String endDate;

  public PeriodDto(String startDate, String endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }
}
