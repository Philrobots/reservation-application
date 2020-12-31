package ca.ulaval.glo4002.reservation.domain.material;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.report.ReportPeriod;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;

public class Buffet {
  private final Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities = new HashMap<>();

  private final DailyDishesQuantityFactory dailyDishesQuantityFactory;

  public Buffet(DailyDishesQuantityFactory dailyDishesQuantityFactory) {
    this.dailyDishesQuantityFactory = dailyDishesQuantityFactory;
  }

  public Map<LocalDate, DailyDishesQuantity> getAllDailyDishesQuantity() {
    return dailyDishesQuantities;
  }

  public Map<LocalDate, DailyDishesQuantity> getDishesQuantityByDate(ReportPeriod reportPeriod) {
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantitiesForPeriod = new HashMap<>();
    if (dailyDishesQuantities.isEmpty()) {
      return dailyDishesQuantitiesForPeriod;
    }
    addAllDailyDishesQuantityWithinReportPeriod(dailyDishesQuantitiesForPeriod, reportPeriod);
    addLastDailyDishesBeforeReportPeriodStart(dailyDishesQuantitiesForPeriod, reportPeriod);
    return dailyDishesQuantitiesForPeriod;
  }

  public void updateDailyDishesQuantity(Reservation reservation) {
    LocalDate dinnerDate = reservation.getDinnerDate().toLocalDate();
    if (dailyDishesQuantities.containsKey(dinnerDate)) {
      updateExistingDishesQuantity(reservation);
    } else {
      dailyDishesQuantities.put(dinnerDate, dailyDishesQuantityFactory.create(reservation));
    }
  }

  private void addAllDailyDishesQuantityWithinReportPeriod(Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities,
                                                           ReportPeriod reportPeriod)
  {
    for (LocalDate date : reportPeriod.getAllDaysOfPeriod()) {
      if (this.dailyDishesQuantities.containsKey(date)) {
        dailyDishesQuantities.put(date, this.dailyDishesQuantities.get(date));
      }
    }
  }

  private void addLastDailyDishesBeforeReportPeriodStart(Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities,
                                                         ReportPeriod reportPeriod)
  {
    LocalDate lastDateBeforePeriodStart = getLastDateBeforeReportPeriodStart(reportPeriod);
    if (!lastDateBeforePeriodStart.isEqual(reportPeriod.getStartDate())) {
      dailyDishesQuantities.put(lastDateBeforePeriodStart,
                                this.dailyDishesQuantities.get(lastDateBeforePeriodStart));
    }
  }

  private void updateExistingDishesQuantity(Reservation reservation) {
    LocalDate dinnerDate = reservation.getDinnerDate().toLocalDate();
    DailyDishesQuantity existingDailyDishesQuantity = dailyDishesQuantities.get(dinnerDate);
    existingDailyDishesQuantity.updateQuantity(reservation.getNumberOfCustomers(),
                                               reservation.getNumberOfRestrictions());
    dailyDishesQuantities.put(dinnerDate, existingDailyDishesQuantity);
  }

  private LocalDate getLastDateBeforeReportPeriodStart(ReportPeriod reportPeriod) {
    LocalDate lastDateBeforeStart = getSmallestDate();
    for (LocalDate date : dailyDishesQuantities.keySet()) {
      if (date.isBefore(reportPeriod.getStartDate()) && date.isAfter(lastDateBeforeStart)) {
        lastDateBeforeStart = date;
      }
    }
    return lastDateBeforeStart;
  }

  private LocalDate getSmallestDate() {
    List<LocalDate> dates = new ArrayList<>(dailyDishesQuantities.keySet());
    LocalDate smallestDate = dates.get(0);
    for (LocalDate date : dates) {
      if (date.isBefore(smallestDate)) {
        smallestDate = date;
      }
    }
    return smallestDate;
  }
}
