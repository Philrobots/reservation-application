package ca.ulaval.glo4002.reservation.service.report.chef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefReport;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefReportGenerator;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefRepository;

public class ChefReportService {
  private final ChefReportGenerator chefReportGenerator;
  private final ChefRepository chefRepository;

  public ChefReportService(ChefReportGenerator chefReportGenerator, ChefRepository chefRepository) {
    this.chefReportGenerator = chefReportGenerator;
    this.chefRepository = chefRepository;
  }

  public ChefReport getChefReport() {
    Map<LocalDate, Set<Chef>> chefsByDate = chefRepository.getAllChefsWorkSchedule();
    return chefReportGenerator.generateReport(chefsByDate);
  }

  public BigDecimal getTotalChefFees() {
    return getChefReport().getTotalChefPrice();
  }
}
