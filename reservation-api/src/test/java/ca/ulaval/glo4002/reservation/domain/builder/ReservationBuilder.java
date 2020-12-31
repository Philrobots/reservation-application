package ca.ulaval.glo4002.reservation.domain.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.reservation.domain.date.DinnerDate;
import ca.ulaval.glo4002.reservation.domain.date.ReservationDate;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.VendorId;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;

public class ReservationBuilder {
  private static final LocalDateTime A_DINNER_DATE = LocalDateTime.now();
  private static final LocalDateTime A_RESERVATION_DATE = LocalDateTime.now();

  private ReservationNumber id = new VendorId();
  private DinnerDate dinnerDate = new DinnerDate(A_DINNER_DATE);
  private final List<Table> tables = new ArrayList<>();
  private final ReservationDate reservationDate = new ReservationDate(A_RESERVATION_DATE);

  public ReservationBuilder withDinnerDate(LocalDateTime dinnerDate) {
    this.dinnerDate = new DinnerDate(dinnerDate);
    return this;
  }

  public ReservationBuilder withTable(Table table) {
    tables.add(table);
    return this;
  }

  public Reservation build() {
    return new Reservation(id, dinnerDate, tables, reservationDate);
  }
}
