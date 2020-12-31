package ca.ulaval.glo4002.reservation.api.configuration.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class PeriodApiDto {
  @NotNull
  private String beginDate;

  @NotNull
  private String endDate;

  public String getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public List<String> getDates() {
    List<String> dates = new ArrayList<>();
    dates.add(this.beginDate);
    dates.add(this.endDate);
    return dates;
  }
}
