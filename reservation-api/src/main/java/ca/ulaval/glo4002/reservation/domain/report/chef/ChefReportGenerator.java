package ca.ulaval.glo4002.reservation.domain.report.chef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;

public class ChefReportGenerator {

  public ChefReport generateReport(Map<LocalDate, Set<Chef>> restaurantChefByDate) {
    ChefReport chefReport = new ChefReport();
    restaurantChefByDate.forEach((date, chefs) -> chefReport.addChefReportInformation(date.toString(), chefs, calculatePrice(chefs)));
    return chefReport;
  }

  private BigDecimal calculatePrice(Set<Chef> chefs) {
    BigDecimal totalChefsPrice = BigDecimal.ZERO;
    for (Chef chef : chefs) {
      totalChefsPrice = totalChefsPrice.add(chef.getPrice());
    }
    return totalChefsPrice;
  }
}
