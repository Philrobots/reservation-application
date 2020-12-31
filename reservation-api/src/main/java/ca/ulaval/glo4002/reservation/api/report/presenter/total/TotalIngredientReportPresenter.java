package ca.ulaval.glo4002.reservation.api.report.presenter.total;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.reservation.api.report.dto.TotalReportDto;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportPresenter;

public class TotalIngredientReportPresenter implements IngredientReportPresenter {
  private final TotalReportDtoFactory totalReportDtoFactory;

  public TotalIngredientReportPresenter(TotalReportDtoFactory totalReportDtoFactory) {
    this.totalReportDtoFactory = totalReportDtoFactory;
  }

  public Response presentReport(IngredientReport ingredientReport) {
    TotalReportDto totalReportDto = totalReportDtoFactory.create(ingredientReport);
    return Response.ok().entity(totalReportDto).build();
  }
}
