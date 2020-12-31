package ca.ulaval.glo4002.reservation.api.report;

import ca.ulaval.glo4002.reservation.api.report.presenter.total.TotalIngredientReportPresenter;
import ca.ulaval.glo4002.reservation.api.report.presenter.total.TotalReportDtoFactory;
import ca.ulaval.glo4002.reservation.api.report.presenter.unit.UnitIngredientReportPresenter;
import ca.ulaval.glo4002.reservation.api.report.presenter.unit.UnitReportDtoFactory;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportPresenter;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportType;

public class IngredientReportPresenterFactory {
  private final UnitReportDtoFactory unitReportDtoFactory;
  private final TotalReportDtoFactory totalReportDtoFactory;

  public IngredientReportPresenterFactory(UnitReportDtoFactory unitReportDtoFactory,
                                          TotalReportDtoFactory totalReportDtoFactory)
  {
    this.unitReportDtoFactory = unitReportDtoFactory;
    this.totalReportDtoFactory = totalReportDtoFactory;
  }

  public IngredientReportPresenter create(IngredientReportType ingredientReportType) {
    if (ingredientReportType.equals(IngredientReportType.UNIT)) {
      return new UnitIngredientReportPresenter(unitReportDtoFactory);
    } else {
      return new TotalIngredientReportPresenter(totalReportDtoFactory);
    }
  }
}
