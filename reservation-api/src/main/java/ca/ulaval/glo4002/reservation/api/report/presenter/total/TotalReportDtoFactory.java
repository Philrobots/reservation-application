package ca.ulaval.glo4002.reservation.api.report.presenter.total;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.api.report.dto.TotalReportDto;
import ca.ulaval.glo4002.reservation.api.report.presenter.IngredientReportInformationDto;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportInformation;

public class TotalReportDtoFactory {
  public TotalReportDto create(IngredientReport ingredientReport) {
    List<IngredientReportInformationDto> ingredientsInfo = extractIngredientReportInformationDto(ingredientReport);
    return new TotalReportDto(ingredientsInfo,
                              ingredientReport.calculateTotalPriceForEntireReport());
  }

  private List<IngredientReportInformationDto> extractIngredientReportInformationDto(IngredientReport ingredientReport) {
    List<IngredientReportInformation> ingredientsReportInformation = new ArrayList<>(ingredientReport.generateTotalIngredientReportInformation()
                                                                                                     .values());
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
