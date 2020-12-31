package ca.ulaval.glo4002.reservation.service;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.configuration.dto.CreateConfigurationRequestDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.CreateReservationRequestDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDto;
import ca.ulaval.glo4002.reservation.domain.ReservationRequest;
import ca.ulaval.glo4002.reservation.domain.Restaurant;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningConfigurationRequest;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.service.reservation.assembler.ConfigurationRequestAssembler;
import ca.ulaval.glo4002.reservation.service.reservation.assembler.ReservationAssembler;
import ca.ulaval.glo4002.reservation.service.reservation.assembler.ReservationRequestAssembler;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

  @Mock
  private CreateReservationRequestDto createReservationRequestDto;

  @Mock
  private ReservationAssembler reservationAssembler;

  @Mock
  private ReservationRequestAssembler reservationRequestAssembler;

  @Mock
  private ReservationNumber reservationId;

  @Mock
  private Restaurant restaurant;

  @Mock
  private ReservationRequest reservationRequest;

  @Mock
  private Reservation reservation;

  @Mock
  private ReservationDto expectedReservationDto;

  @Mock
  private ConfigurationRequestAssembler configurationRequestAssembler;

  @Mock
  private HoppeningConfigurationRequest hoppeningConfigurationRequest;

  @Mock
  private CreateConfigurationRequestDto createConfigurationRequestDto;
  
  private RestaurantService restaurantService;

  @BeforeEach
  public void setUpReservationService() {

    restaurantService = new RestaurantService(reservationAssembler,
                                              reservationRequestAssembler,
                                              configurationRequestAssembler,
                                              restaurant);

  }

  @Test
  public void whenMakeReservation_thenReservationRequestIsMadeToRestaurant() {
    // given
    given(reservationRequestAssembler.assemble(createReservationRequestDto)).willReturn(reservationRequest);

    // when
    restaurantService.makeReservation(createReservationRequestDto);

    // then
    verify(restaurant).makeReservation(reservationRequest);
  }

  @Test
  public void whenGetReservation_thenReservationDtoIsAssembled() {
    // given
    given(restaurant.getReservation(reservationId)).willReturn(reservation);

    // when
    restaurantService.getReservationFromRestaurant(reservationId);

    // then
    verify(reservationAssembler).assembleDtoFromReservation(reservation);
  }

  @Test
  public void whenGetReservation_thenReservationDtoIsReturned() {
    // given
    given(restaurant.getReservation(reservationId)).willReturn(reservation);
    given(reservationAssembler.assembleDtoFromReservation(reservation)).willReturn(expectedReservationDto);

    // when
    ReservationDto reservationDto = restaurantService.getReservationFromRestaurant(reservationId);

    // then
    assertThat(reservationDto).isEqualTo(expectedReservationDto);
  }

  @Test
  public void whenConfigureHoppeningEvent_thenConfigurationRequestIsMadeToRestaurant() {
    // given
    given(configurationRequestAssembler.assemble(createConfigurationRequestDto)).willReturn(hoppeningConfigurationRequest);

    // when
    restaurantService.configureHoppeningEvent(createConfigurationRequestDto);

    // then
    verify(restaurant).configureHoppeningEvent(hoppeningConfigurationRequest);
  }
}
