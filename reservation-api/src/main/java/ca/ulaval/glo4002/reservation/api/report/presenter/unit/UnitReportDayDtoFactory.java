package ca.ulaval.glo4002.reservation.api.report.presenter.unit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.api.report.dto.UnitReportDayDto;
import ca.ulaval.glo4002.reservation.api.report.presenter.IngredientReportInformationDto;
import ca.ulaval.glo4002.reservation.domain.report.DailyIngredientReportInformation;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportInformation;

public class UnitReportDayDtoFactory {
  public UnitReportDayDto create(LocalDate date,
                                 DailyIngredientReportInformation dailyIngredientReportInformation)
  {
    List<IngredientReportInformationDto> ingredientReportInformationDtos = generateIngredientReportInformationDtos(dailyIngredientReportInformation.getIngredientsReportInformation());
    BigDecimal dailyTotalPrice = dailyIngredientReportInformation.calculateDailyTotalPrice();
    return new UnitReportDayDto(date, ingredientReportInformationDtos, dailyTotalPrice);
  }

  private List<IngredientReportInformationDto> generateIngredientReportInformationDtos(Set<IngredientReportInformation> ingredientsReportInformation) {
    return ingredientsReportInformation.stream()
                                       .map(this::assembleIngredientReportInformationDto)
                                       .sorted(Comparator.comparing(IngredientReportInformationDto::getIngredientName))
                                       .collect(Collectors.toList());
  }

  private IngredientReportInformationDto assembleIngredientReportInformationDto(IngredientReportInformation ingredientReportInformation) {
    return new IngredientReportInformationDto(ingredientReportInformation.getIngredientName()
                                                                         .toString(),
                                              ingredientReportInformation.getQuantity(),
                                              ingredientReportInformation.getTotalPrice());
  }
}
