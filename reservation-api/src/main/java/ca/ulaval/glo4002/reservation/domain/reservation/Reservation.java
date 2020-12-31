package ca.ulaval.glo4002.reservation.domain.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import ca.ulaval.glo4002.reservation.domain.date.Date;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;

public class Reservation {

  private final ReservationNumber reservationId;
  private final Date dinnerDate;
  private final List<Table> tables;
  private final Date reservationDate;

  public Reservation(ReservationNumber reservationId,
                     Date dinnerDate,
                     List<Table> tables,
                     Date reservationDate)
  {
    this.reservationId = reservationId;
    this.dinnerDate = dinnerDate;
    this.tables = tables;
    this.reservationDate = reservationDate;
  }

  public ReservationNumber getReservationId() {
    return reservationId;
  }

  public LocalDateTime getDinnerDate() {
    return dinnerDate.getLocalDateTime();
  }

  public List<Table> getTables() {
    return tables;
  }

  public BigDecimal getReservationFees() {
    BigDecimal reservationFees = BigDecimal.ZERO;
    for (Table table : tables) {
      reservationFees = reservationFees.add(table.getTableReservationFees());
    }
    return reservationFees;
  }

  public LocalDateTime getReservationDate() {
    return reservationDate.getLocalDateTime();
  }

  public Map<RestrictionType, Integer> getRestrictionTypeCount() {
    Map<RestrictionType, Integer> restrictionTypeCount = new HashMap<>();
    for (Table table : tables) {
      restrictionTypeCount = mergeCurrentCountWithTableRestrictionCount(restrictionTypeCount,
                                                                        table.getRestrictionTypeCount());
    }
    return restrictionTypeCount;
  }

  public Set<RestrictionType> getRestrictionTypes() {
    Set<RestrictionType> restrictionTypes = new HashSet<>();
    for (Customer customer : this.getCustomers()) {
      Set<RestrictionType> restrictions = customer.getRestrictions()
                                                  .isEmpty() ? Set.of(RestrictionType.NONE)
                                                             : customer.getRestrictions();
      restrictionTypes.addAll(restrictions);
    }
    return restrictionTypes;
  }

  public int getNumberOfCustomers() {
    return this.getCustomers().size();
  }

  public List<Customer> getCustomers() {
    List<Customer> customers = new ArrayList<>();
    for (Table table : this.tables) {
      customers.addAll(table.getCustomers());
    }
    return customers;
  }

  public int getNumberOfRestrictions() {
    int numberOfRestrictions = 0;
    Map<RestrictionType, Integer> countPerRestrictionType = getRestrictionTypeCount();
    for (RestrictionType restrictionType : countPerRestrictionType.keySet()) {
      if (!restrictionType.equals(RestrictionType.NONE))
        numberOfRestrictions += countPerRestrictionType.get(restrictionType);
    }
    return numberOfRestrictions;
  }

  private Map<RestrictionType, Integer> mergeCurrentCountWithTableRestrictionCount(Map<RestrictionType, Integer> currentCount,
                                                                                   Map<RestrictionType, Integer> tableCount)
  {
    Map<RestrictionType, Integer> updatedCount = new HashMap<>(currentCount);
    for (RestrictionType restrictionType : tableCount.keySet()) {
      if (updatedCount.containsKey(restrictionType)) {
        updatedCount.replace(restrictionType,
                             updatedCount.get(restrictionType) + tableCount.get(restrictionType));
      } else {
        updatedCount.put(restrictionType, tableCount.get(restrictionType));
      }
    }
    return updatedCount;
  }
}
