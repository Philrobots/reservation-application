package ca.ulaval.glo4002.reservation.domain.hoppening;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.exception.InvalidTimeFrameException;
import ca.ulaval.glo4002.reservation.service.reservation.dto.PeriodDto;

@ExtendWith(MockitoExtension.class)
public class HoppeningConfigurationRequestFactoryTest {
  private static final String FIRST_CHRONOLOGICAL_DATE = "2150-01-01";
  private static final String SECOND_CHRONOLOGICAL_DATE = "2150-07-16";
  private static final String THIRD_CHRONOLOGICAL_DATE = "2150-07-20";
  private static final String LAST_CHRONOLOGICAL_DATE = "2150-07-30";
  private static final LocalDate FIRST_CHRONOLOGICAL_LOCALDATE = LocalDate.of(2150, 1, 1);
  private static final LocalDate SECOND_CHRONOLOGICAL_LOCALDATE = LocalDate.of(2150, 7, 16);
  private static final LocalDate THIRD_CHRONOLOGICAL_LOCALDATE = LocalDate.of(2150, 7, 20);
  private static final LocalDate LAST_CHRONOLOGICAL_LOCALDATE = LocalDate.of(2150, 7, 30);

  private HoppeningConfigurationRequestFactory hoppeningConfigurationRequestFactory;

  @BeforeEach
  public void setUpConfigurationRequestFactory() {
    hoppeningConfigurationRequestFactory = new HoppeningConfigurationRequestFactory();
  }

  @Test
  public void givenValidPeriods_whenCreate_thenHoppeningConfigurationRequestIsCreated() {
    // given
    PeriodDto reservationPeriod = createPeriodValueObject(FIRST_CHRONOLOGICAL_DATE,
                                                          SECOND_CHRONOLOGICAL_DATE);
    PeriodDto dinnerPeriod = createPeriodValueObject(THIRD_CHRONOLOGICAL_DATE,
                                                     LAST_CHRONOLOGICAL_DATE);

    // when
    HoppeningConfigurationRequest hoppeningConfigurationRequest = hoppeningConfigurationRequestFactory.create(dinnerPeriod,
                                                                                                              reservationPeriod);

    // then
    assertThat(hoppeningConfigurationRequest.getReservationDinerPeriod()
                                            .getStartDate()).isEqualTo(FIRST_CHRONOLOGICAL_LOCALDATE);
    assertThat(hoppeningConfigurationRequest.getReservationDinerPeriod()
                                            .getEndDate()).isEqualTo(SECOND_CHRONOLOGICAL_LOCALDATE);
    assertThat(hoppeningConfigurationRequest.getDinnerDinerPeriod()
                                            .getStartDate()).isEqualTo(THIRD_CHRONOLOGICAL_LOCALDATE);
    assertThat(hoppeningConfigurationRequest.getDinnerDinerPeriod()
                                            .getEndDate()).isEqualTo(LAST_CHRONOLOGICAL_LOCALDATE);
  }

  @Test
  public void givenInvalidReservationPeriod_whenCreate_thenTheHoppeningDateAreNotChenge() {
    // given
    PeriodDto reservationPeriod = createPeriodValueObject(SECOND_CHRONOLOGICAL_DATE,
                                                          FIRST_CHRONOLOGICAL_DATE);
    PeriodDto dinnerPeriod = createPeriodValueObject(THIRD_CHRONOLOGICAL_DATE,
                                                     LAST_CHRONOLOGICAL_DATE);

    // when
    Executable hoppeningConfigurationRequest = () -> hoppeningConfigurationRequestFactory.create(dinnerPeriod,
                                                                                                 reservationPeriod);

    // then
    assertThrows(InvalidTimeFrameException.class, hoppeningConfigurationRequest);
  }

  @Test
  public void givenInvalidDinnerPeriod_whenCreate_thenTheHoppeningDateAreNotChenge() {
    // given
    PeriodDto reservationPeriod = createPeriodValueObject(FIRST_CHRONOLOGICAL_DATE,
                                                          SECOND_CHRONOLOGICAL_DATE);
    PeriodDto dinnerPeriod = createPeriodValueObject(LAST_CHRONOLOGICAL_DATE,
                                                     THIRD_CHRONOLOGICAL_DATE);

    // when
    Executable hoppeningConfigurationRequest = () -> hoppeningConfigurationRequestFactory.create(dinnerPeriod,
                                                                                                 reservationPeriod);

    // then
    assertThrows(InvalidTimeFrameException.class, hoppeningConfigurationRequest);
  }

  @Test
  public void givenOverlappingPeriod_whenCreate_thenTheHoppeningDateAreNotChenge() {
    // given
    PeriodDto reservationPeriod = createPeriodValueObject(FIRST_CHRONOLOGICAL_DATE,
                                                          THIRD_CHRONOLOGICAL_DATE);
    PeriodDto dinnerPeriod = createPeriodValueObject(SECOND_CHRONOLOGICAL_DATE,
                                                     LAST_CHRONOLOGICAL_DATE);

    // when
    Executable hoppeningConfigurationRequest = () -> hoppeningConfigurationRequestFactory.create(dinnerPeriod,
                                                                                                 reservationPeriod);

    // then
    assertThrows(InvalidTimeFrameException.class, hoppeningConfigurationRequest);
  }

  private PeriodDto createPeriodValueObject(String startDate, String endDate) {
    return new PeriodDto(startDate, endDate);
  }
}
