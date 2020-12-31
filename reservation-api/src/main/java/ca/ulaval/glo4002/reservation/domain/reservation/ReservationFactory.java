package ca.ulaval.glo4002.reservation.domain.reservation;

import java.util.List;

import ca.ulaval.glo4002.reservation.domain.ReservationRequest;
import ca.ulaval.glo4002.reservation.domain.date.*;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningEvent;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import ca.ulaval.glo4002.reservation.domain.reservation.table.TableFactory;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.VendorId;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;

public class ReservationFactory {
  private final DateFactory dateFactory;
  private final TableFactory tableFactory;

  public ReservationFactory(DateFactory dateFactory, TableFactory tableFactory)
  {
    this.dateFactory = dateFactory;
    this.tableFactory = tableFactory;
  }

  public Reservation create(ReservationRequest reservationRequest, HoppeningEvent hoppeningEvent) {
    ReservationNumber reservationId = new VendorId(reservationRequest.getVendorCode());
    Date dinnerDate = dateFactory.createDinnerDate(reservationRequest.getDinnerDate(), hoppeningEvent.getDinnerDinerPeriod());
    Date reservationDate = dateFactory.createReservationDate(reservationRequest.getReservationDate(), hoppeningEvent.getReservationDinerPeriod());
    List<Table> tables = tableFactory.createTables(reservationRequest.getTables());
    return new Reservation(reservationId, dinnerDate, tables, reservationDate);
  }
}
