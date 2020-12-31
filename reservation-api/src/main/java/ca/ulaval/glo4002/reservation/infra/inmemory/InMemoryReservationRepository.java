package ca.ulaval.glo4002.reservation.infra.inmemory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;
import ca.ulaval.glo4002.reservation.service.reservation.exception.ReservationNotFoundException;

public class InMemoryReservationRepository implements ReservationRepository {
  private final List<Reservation> reservations = new ArrayList<>();

  public ReservationNumber saveReservation(Reservation reservation) {
    reservations.add(reservation);
    return reservation.getReservationId();
  }

  public Reservation getReservationById(ReservationNumber reservationId) {
    for (Reservation reservation : reservations) {
      if (reservationId.equals(reservation.getReservationId())) {
        return reservation;
      }
    }
    throw new ReservationNotFoundException(reservationId);
  }

  public List<Reservation> getReservationsByDate(LocalDateTime date) {
    return reservations.stream().filter(reservation -> reservation.getDinnerDate().toLocalDate().isEqual(date.toLocalDate())).collect(Collectors.toList());
  }

  public BigDecimal getTotalReservationIncome() {
    BigDecimal totalPrice = BigDecimal.ZERO;
    for (Reservation reservation : reservations) {
      totalPrice = totalPrice.add(reservation.getReservationFees());
    }
    return totalPrice;
  }
}
