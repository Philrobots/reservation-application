package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import ca.ulaval.glo4002.reservation.api.configuration.dto.CreateConfigurationRequestDto;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningConfigurationRequest;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningConfigurationRequestFactory;
import ca.ulaval.glo4002.reservation.service.reservation.dto.PeriodDto;

public class ConfigurationRequestAssembler {
  private final PeriodDtoAssembler periodDtoAssembler;
  private final HoppeningConfigurationRequestFactory hoppeningConfigurationRequestFactory;

  public ConfigurationRequestAssembler(PeriodDtoAssembler eventPeriodDtoAssembler,
                                       HoppeningConfigurationRequestFactory hoppeningConfigurationRequestFactory)
  {
    this.hoppeningConfigurationRequestFactory = hoppeningConfigurationRequestFactory;
    this.periodDtoAssembler = eventPeriodDtoAssembler;
  }

  public HoppeningConfigurationRequest assemble(CreateConfigurationRequestDto createConfigurationRequestDto) {
    PeriodDto dinnerPeriod = periodDtoAssembler.assemble(createConfigurationRequestDto.getDinnerPeriod());
    PeriodDto reservationPeriod = periodDtoAssembler.assemble(createConfigurationRequestDto.getReservationPeriod());

    return hoppeningConfigurationRequestFactory.create(dinnerPeriod, reservationPeriod);
  }
}
