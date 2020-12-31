package ca.ulaval.glo4002.reservation.domain.hoppening;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ca.ulaval.glo4002.reservation.domain.date.DinerPeriod;
import ca.ulaval.glo4002.reservation.domain.exception.InvalidTimeFrameException;
import ca.ulaval.glo4002.reservation.service.reservation.dto.PeriodDto;

public class HoppeningConfigurationRequestFactory {
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  public HoppeningConfigurationRequest create(PeriodDto dinnerPeriodValueObject,
                                              PeriodDto reservationPeriodValueObject)
  {
    DinerPeriod dinnerDinerPeriod = createPeriod(dinnerPeriodValueObject.getStartDate(),
                                       dinnerPeriodValueObject.getEndDate());
    DinerPeriod reservationDinerPeriod = createPeriod(reservationPeriodValueObject.getStartDate(),
                                            reservationPeriodValueObject.getEndDate());

    if (!isConfigurationRequestValid(dinnerDinerPeriod, reservationDinerPeriod)) {
      throw new InvalidTimeFrameException();
    }

    return new HoppeningConfigurationRequest(dinnerDinerPeriod, reservationDinerPeriod);
  }

  private DinerPeriod createPeriod(String startDate, String endDate) {
    LocalDate startDateToLocalDate = LocalDate.parse(startDate, dateFormatter);
    LocalDate endDateToLocalDate = LocalDate.parse(endDate, dateFormatter);

    return new DinerPeriod(startDateToLocalDate, endDateToLocalDate);
  }

  private Boolean isConfigurationRequestValid(DinerPeriod dinnerDinerPeriod, DinerPeriod reservationDinerPeriod) {
    return isTimeFrameValid(dinnerDinerPeriod.getStartDate(), dinnerDinerPeriod.getEndDate())
           && isTimeFrameValid(reservationDinerPeriod.getStartDate(), reservationDinerPeriod.getEndDate())
           && isTimeFrameValid(reservationDinerPeriod.getEndDate(), dinnerDinerPeriod.getStartDate());
  }

  private Boolean isTimeFrameValid(LocalDate startDate, LocalDate endDate) {
    return endDate.isAfter(startDate);
  }
}
