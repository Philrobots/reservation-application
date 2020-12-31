package ca.ulaval.glo4002.reservation.service.reservation.exception;

import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;
import ca.ulaval.glo4002.reservation.domain.exception.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
  private static final String ERROR_CODE = "RESERVATION_NOT_FOUND";
  private static final String ERROR_MESSAGE = "Reservation with number RESERVATION_NUMBER not found";

  public ReservationNotFoundException(ReservationNumber reservationId) {
    super(ERROR_CODE,
          ERROR_MESSAGE.replace("RESERVATION_NUMBER", String.valueOf(reservationId.getReservationNumber())));
  }
}
