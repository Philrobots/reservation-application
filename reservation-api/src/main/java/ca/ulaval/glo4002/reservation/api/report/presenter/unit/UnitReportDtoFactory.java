package ca.ulaval.glo4002.reservation.api.report.presenter.unit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.reservation.api.report.dto.UnitReportDayDto;
import ca.ulaval.glo4002.reservation.api.report.dto.UnitReportDto;
import ca.ulaval.glo4002.reservation.domain.report.DailyIngredientReportInformation;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;

public class UnitReportDtoFactory {
  private final UnitReportDayDtoFactory unitReportDayDtoFactory;

  public UnitReportDtoFactory(UnitReportDayDtoFactory unitReportDayDtoFactory) {
    this.unitReportDayDtoFactory = unitReportDayDtoFactory;
  }

  public UnitReportDto create(IngredientReport ingredientReport) {
    List<UnitReportDayDto> ingredientsInfo = generateDailyIngredientReportInformationDto(ingredientReport);
    return new UnitReportDto(ingredientsInfo);
  }

  private List<UnitReportDayDto> generateDailyIngredientReportInformationDto(IngredientReport ingredientReport) {
    List<UnitReportDayDto> dailyIngredientReportInformationDtos = new ArrayList<>();
    for (Map.Entry<LocalDate, DailyIngredientReportInformation> dailyIngredientInformationEntry : ingredientReport.getDailyIngredientsInformation()
                                                                                                                  .entrySet())
    {
      dailyIngredientReportInformationDtos.add(unitReportDayDtoFactory.create(dailyIngredientInformationEntry.getKey(),
                                                                              dailyIngredientInformationEntry.getValue()));
    }
    dailyIngredientReportInformationDtos.sort(Comparator.comparing(UnitReportDayDto::getDate));
    return dailyIngredientReportInformationDtos;
  }
}
