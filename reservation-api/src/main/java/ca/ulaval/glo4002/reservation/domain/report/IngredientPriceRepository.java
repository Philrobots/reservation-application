package ca.ulaval.glo4002.reservation.domain.report;

import java.util.List;

import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

public interface IngredientPriceRepository {
  List<IngredientPriceDto> getIngredientsPrice();
}
