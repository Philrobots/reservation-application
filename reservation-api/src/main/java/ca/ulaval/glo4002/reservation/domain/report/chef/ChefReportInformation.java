package ca.ulaval.glo4002.reservation.domain.report.chef;

import java.math.BigDecimal;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;

public class ChefReportInformation {
  private final Set<Chef> chefs;
  private final String date;
  private final BigDecimal totalPrice;

  public ChefReportInformation(Set<Chef> chefs, String date, BigDecimal totalPrice) {
    this.chefs = chefs;
    this.date = date;
    this.totalPrice = totalPrice;
  }

  public Set<Chef> getChefs() {
    return chefs;
  }

  public String getDate() {
    return date;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }
}
