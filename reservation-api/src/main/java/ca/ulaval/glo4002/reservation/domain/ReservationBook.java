package ca.ulaval.glo4002.reservation.domain;

import java.time.LocalDateTime;
import java.util.List;

import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;

public class ReservationBook {
  private final ReservationRepository reservationRepository;

  public ReservationBook(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public void register(Reservation reservation) {
    reservationRepository.saveReservation(reservation);
  }

  public Reservation getReservation(ReservationNumber reservationId) {
    return reservationRepository.getReservationById(reservationId);
  }

  public List<Reservation> getReservationsByDate(LocalDateTime date) {
    return reservationRepository.getReservationsByDate(date);
  }

  public int getNumberOfCustomersForADay(LocalDateTime dinnerDate) {
    List<Reservation> reservations = reservationRepository.getReservationsByDate(dinnerDate);
    return reservations.stream().mapToInt(Reservation::getNumberOfCustomers).sum();
  }
}
