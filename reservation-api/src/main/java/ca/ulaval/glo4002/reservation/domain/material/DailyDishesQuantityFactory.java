package ca.ulaval.glo4002.reservation.domain.material;

import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;

public class DailyDishesQuantityFactory {
  public DailyDishesQuantity create(Reservation reservation) {
    return new DailyDishesQuantity(reservation.getNumberOfCustomers(),
                                   reservation.getNumberOfRestrictions());
  }
}
