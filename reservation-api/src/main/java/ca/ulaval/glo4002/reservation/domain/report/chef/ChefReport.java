package ca.ulaval.glo4002.reservation.domain.report.chef;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;

public class ChefReport {
  private final List<ChefReportInformation> chefReportInformations = new ArrayList<>();
  private final Comparator<ChefReportInformation> comparator = new ChefReportInformationComparator();

  public void addChefReportInformation(String date, Set<Chef> chefs, BigDecimal totalChefPrice) {
    ChefReportInformation chefReportInformation = new ChefReportInformation(chefs, date, totalChefPrice);
    this.chefReportInformations.add(chefReportInformation);
    this.chefReportInformations.sort(comparator);
  }

  public List<ChefReportInformation> getChefReportInformations() {
    return chefReportInformations;
  }

  public BigDecimal getTotalChefPrice() {
    BigDecimal totalPrice = BigDecimal.ZERO;
    for (ChefReportInformation chefReportInformation : chefReportInformations) {
      totalPrice = totalPrice.add(chefReportInformation.getTotalPrice());
    }
    return totalPrice;
  }
}