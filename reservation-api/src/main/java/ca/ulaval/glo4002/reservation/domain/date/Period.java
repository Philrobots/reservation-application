package ca.ulaval.glo4002.reservation.domain.date;

import java.time.LocalDate;

public interface Period {
    LocalDate getStartDate();

    LocalDate getEndDate();

    boolean isWithinPeriod(LocalDate date);
}
