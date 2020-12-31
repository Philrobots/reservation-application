package ca.ulaval.glo4002.reservation.domain.report;

import javax.ws.rs.core.Response;

public interface IngredientReportPresenter {
  Response presentReport(IngredientReport ingredientReport);
}
