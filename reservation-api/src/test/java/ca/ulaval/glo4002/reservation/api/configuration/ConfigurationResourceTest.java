package ca.ulaval.glo4002.reservation.api.configuration;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.configuration.dto.CreateConfigurationRequestDto;
import ca.ulaval.glo4002.reservation.api.configuration.dto.PeriodApiDto;
import ca.ulaval.glo4002.reservation.api.configuration.validator.ConfigurationDateFormatValidator;
import ca.ulaval.glo4002.reservation.service.RestaurantService;

@ExtendWith(MockitoExtension.class)
public class ConfigurationResourceTest {

  private static final String A_HOPPENING_BEGIN_DATE = "2150-07-20";
  private static final String A_HOPPENING_END_DATE = "2150-07-30";
  private static final String A_RESERVATION_BEGIN_DATE = "2150-01-01";
  private static final String A_RESERVATION_END_DATE = "2150-07-16";

  @Mock
  private RestaurantService restaurantService;

  @Mock
  private ConfigurationDateFormatValidator configurationDateFormatValidator;

  private ConfigurationResource configurationResource;

  @BeforeEach
  public void setUpConfigurationResource() {
    configurationResource = new ConfigurationResource(restaurantService,
                                                      configurationDateFormatValidator);

  }

  @Test
  public void whenCreateConfiguration_thenConfigurationRequestIsCalled() {
    // given
    CreateConfigurationRequestDto createConfigurationRequestDto = buildConfigurationDto();

    // when
    configurationResource.createConfiguration(createConfigurationRequestDto);

    // then
    verify(restaurantService).configureHoppeningEvent(createConfigurationRequestDto);
  }

  private CreateConfigurationRequestDto buildConfigurationDto() {
    PeriodApiDto hoppeningDto = buildPeriodDto(ConfigurationResourceTest.A_HOPPENING_BEGIN_DATE,
                                               ConfigurationResourceTest.A_HOPPENING_END_DATE);
    PeriodApiDto reservationPeriodApiDto = buildPeriodDto(ConfigurationResourceTest.A_RESERVATION_BEGIN_DATE,
                                                          ConfigurationResourceTest.A_RESERVATION_END_DATE);

    CreateConfigurationRequestDto createConfigurationRequestDto = new CreateConfigurationRequestDto();
    createConfigurationRequestDto.setDinnerPeriod(hoppeningDto);
    createConfigurationRequestDto.setReservationPeriod(reservationPeriodApiDto);

    return createConfigurationRequestDto;
  }

  private PeriodApiDto buildPeriodDto(String beginDate, String endDate) {
    PeriodApiDto periodApiDto = new PeriodApiDto();
    periodApiDto.setBeginDate(beginDate);
    periodApiDto.setEndDate(endDate);

    return periodApiDto;
  }
}
