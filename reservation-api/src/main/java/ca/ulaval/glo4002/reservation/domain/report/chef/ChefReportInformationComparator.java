package ca.ulaval.glo4002.reservation.domain.report.chef;

import java.time.LocalDate;
import java.util.Comparator;

public class ChefReportInformationComparator implements Comparator<ChefReportInformation> {
  @Override
  public int compare(ChefReportInformation chefReportInformation, ChefReportInformation t1) {
    LocalDate firstDate = LocalDate.parse(chefReportInformation.getDate());
    LocalDate secondDate = LocalDate.parse(t1.getDate());

    if (firstDate.equals(secondDate)) {
      return 0;
    }
    if (firstDate.isAfter(secondDate)) {
      return 1;
    }
    return -1;
  }
}
