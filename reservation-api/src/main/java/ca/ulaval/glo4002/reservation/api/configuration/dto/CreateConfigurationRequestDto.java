package ca.ulaval.glo4002.reservation.api.configuration.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateConfigurationRequestDto {
  @Valid
  @NotNull
  private PeriodApiDto reservationPeriod;

  @Valid
  @NotNull
  @JsonProperty("hoppening")
  private PeriodApiDto dinnerPeriod;

  public PeriodApiDto getReservationPeriod() {
    return reservationPeriod;
  }

  public void setReservationPeriod(PeriodApiDto reservationPeriod) {
    this.reservationPeriod = reservationPeriod;
  }

  public List<String> getDates() {
    List<String> dates = new ArrayList<>();
    dates.addAll(dinnerPeriod.getDates());
    dates.addAll(reservationPeriod.getDates());
    return dates;
  }

  public PeriodApiDto getDinnerPeriod() {
    return dinnerPeriod;
  }

  public void setDinnerPeriod(PeriodApiDto dinnerPeriod) {
    this.dinnerPeriod = dinnerPeriod;
  }
}
