package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import ca.ulaval.glo4002.reservation.api.configuration.dto.PeriodApiDto;
import ca.ulaval.glo4002.reservation.service.reservation.dto.PeriodDto;

public class PeriodDtoAssembler {
  public PeriodDto assemble(PeriodApiDto eventPeriodDto) {
    return new PeriodDto(eventPeriodDto.getBeginDate(), eventPeriodDto.getEndDate());
  }
}
