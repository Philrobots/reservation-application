package ca.ulaval.glo4002.reservation.api.report.assembler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.api.report.dto.ChefReportDto;
import ca.ulaval.glo4002.reservation.api.report.dto.ChefReportInformationDto;
import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefReport;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefReportInformation;

public class ChefReportDtoAssembler {
  public ChefReportDto assembleChefReportDto(ChefReport chefReport) {
    List<ChefReportInformation> chefReportInformation = chefReport.getChefReportInformations();
    List<ChefReportInformationDto> chefReportInformationDto = chefReportInformation.stream()
                                                     .map(this::assembleChefReportInformationDto)
                                                     .collect(Collectors.toList());

    return new ChefReportDto(chefReportInformationDto);
  }

  private ChefReportInformationDto assembleChefReportInformationDto(ChefReportInformation chefReportInformation) {
    List<String> chefsName = getChefsName(chefReportInformation.getChefs());
    return new ChefReportInformationDto(chefReportInformation.getDate(),
                                        chefsName,
                                        chefReportInformation.getTotalPrice());
  }

  private List<String> getChefsName(Set<Chef> chefs) {
    return chefs.stream().map(Chef::getName).sorted().collect(Collectors.toList());
  }
}