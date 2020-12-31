package ca.ulaval.glo4002.reservation.api.report.validator;

import ca.ulaval.glo4002.reservation.domain.report.exception.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.service.report.chef.ReportService;

public class ReportDateValidator {
  private final String reportDateRegex;
  private final ReportService reportService;

  public ReportDateValidator(String reportDateRegex, ReportService reportService) {
    this.reportDateRegex = reportDateRegex;
    this.reportService = reportService;
  }

  public void validate(String startDate, String endDate) {
    if (areDatesInvalid(startDate, endDate)) {
      throw new InvalidReportDateException(reportService.getHoppeningStart(),
                                           reportService.getHoppeningEnd());
    }
  }

  private boolean areDatesInvalid(String startDate, String endDate) {
    return startDate == null || endDate == null || !startDate.matches(reportDateRegex)
           || !endDate.matches(reportDateRegex);
  }
}
