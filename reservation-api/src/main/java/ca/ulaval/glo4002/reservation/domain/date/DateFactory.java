package ca.ulaval.glo4002.reservation.domain.date;

import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidDinnerDateException;
import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidReservationDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFactory {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public Date createDinnerDate(String dinnerDateStringify, Period dinerPeriod) {
        validateDinnerDate(dinnerDateStringify, dinerPeriod);
        return new DinnerDate(LocalDateTime.parse(dinnerDateStringify, dateTimeFormatter));
    }

    public ReservationDate createReservationDate(String reservationDateStringify, Period dinerPeriod) {
        validateReservationDate(reservationDateStringify, dinerPeriod);
        return new ReservationDate(LocalDateTime.parse(reservationDateStringify, dateTimeFormatter));
    }

    private void validateReservationDate(String date, Period dinerPeriod) {
        LocalDateTime parsedDate = LocalDateTime.parse(date, dateTimeFormatter);
        if (!dinerPeriod.isWithinPeriod(parsedDate.toLocalDate())) {
            throw new InvalidReservationDateException(dinerPeriod.getStartDate(), dinerPeriod.getEndDate());
        }
    }

    private void validateDinnerDate(String date, Period dinerPeriod) {
        LocalDateTime parsedDate = LocalDateTime.parse(date, dateTimeFormatter);
        if (!dinerPeriod.isWithinPeriod(parsedDate.toLocalDate())) {
            throw new InvalidDinnerDateException(dinerPeriod.getStartDate(), dinerPeriod.getEndDate());
        }
    }
}
