package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
  ReservationNumber saveReservation(Reservation reservation);

  Reservation getReservationById(ReservationNumber reservationId);

  List<Reservation> getReservationsByDate(LocalDateTime date);

  BigDecimal getTotalReservationIncome();
}
