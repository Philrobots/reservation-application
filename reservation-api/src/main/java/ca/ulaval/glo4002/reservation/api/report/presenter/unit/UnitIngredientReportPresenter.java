package ca.ulaval.glo4002.reservation.api.report.presenter.unit;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.reservation.api.report.dto.UnitReportDto;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportPresenter;

public class UnitIngredientReportPresenter implements IngredientReportPresenter {
  private final UnitReportDtoFactory unitReportDtoFactory;

  public UnitIngredientReportPresenter(UnitReportDtoFactory unitReportDtoFactory) {
    this.unitReportDtoFactory = unitReportDtoFactory;
  }

  public Response presentReport(IngredientReport ingredientReport) {
    UnitReportDto unitReportDto = unitReportDtoFactory.create(ingredientReport);
    return Response.ok().entity(unitReportDto).build();
  }
}
