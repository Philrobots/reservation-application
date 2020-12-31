package ca.ulaval.glo4002.reservation.api.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.reservation.builder.CreateReservationRequestDtoBuilder;
import ca.ulaval.glo4002.reservation.api.reservation.builder.ReservationDetailsDtoBuilder;
import ca.ulaval.glo4002.reservation.api.reservation.builder.ReservationDtoBuilder;
import ca.ulaval.glo4002.reservation.api.reservation.dto.CreateReservationRequestDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDetailsDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDto;
import ca.ulaval.glo4002.reservation.api.reservation.exception.InvalidFormatException;
import ca.ulaval.glo4002.reservation.api.reservation.validator.DateFormatValidator;
import ca.ulaval.glo4002.reservation.service.RestaurantService;
import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidDinnerDateException;
import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidReservationDateException;

@ExtendWith(MockitoExtension.class)
public class ReservationResourceTest {

  private static final String AN_ID = "TEAM_1245";
  private static final String LOCATION = "Location";
  private static final String RESERVATIONS_BASE_PATH = "/reservations";
  private static final String INVALID_FORMAT_DATE = "30-21-08";
  private static final String A_OUT_OF_BOUND_DINNER_DATE = "2190-07-30T23:59:59.999Z";
  private static final String A_OUT_OF_BOUND_RESERVATION_DATE = "2149-09-08T23:59:59.999Z";
  private static final LocalDate START_DATE = LocalDate.of(2150, 7, 20);
  private static final LocalDate END_DATE = LocalDate.of(2150, 7, 30);

  @Mock
  private RestaurantService restaurantService;

  @Mock
  private DateFormatValidator dateFormatValidator;

  @Mock
  private ReservationNumber reservationId;

  private ReservationResource reservationResource;

  @BeforeEach
  public void setUp() {
    reservationResource = new ReservationResource(restaurantService, dateFormatValidator);
  }

  @Test
  public void whenCreateNewReservation_thenReservationIsCreated() {
    // given
    given(reservationId.getReservationNumber()).willReturn(AN_ID);
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withAnyTable()
                                                                                                      .build();
    given(restaurantService.makeReservation(createReservationRequestDto)).willReturn(reservationId);

    // when
    reservationResource.createReservation(createReservationRequestDto);

    // then
    verify(restaurantService).makeReservation(createReservationRequestDto);
  }

  @Test
  public void whenCreateNewReservation_thenReturnResponseWithReservationIdInHeader() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withAnyTable()
                                                                                                      .build();
    given(restaurantService.makeReservation(createReservationRequestDto)).willReturn(reservationId);
    given(reservationId.getReservationNumber()).willReturn(AN_ID);

    // when
    Response createReservationResponse = reservationResource.createReservation(createReservationRequestDto);

    // then
    assertThat(createReservationResponse.getHeaderString(LOCATION)).isEqualTo(String.format(RESERVATIONS_BASE_PATH
                                                                                            + "/%s",
                                                                                            AN_ID));
  }

  @Test
  public void givenCreateReservationRequestDtoWithMissingDinnerDate_whenCreateNewReservation_thenThrowInvalidFormatException() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withDinnerDate(null)
                                                                                                      .build();
    InvalidFormatException invalidDinnerDateException = new InvalidFormatException();
    given(restaurantService.makeReservation(createReservationRequestDto)).willThrow(invalidDinnerDateException);

    // when
    Executable creatingReservation = () -> reservationResource.createReservation(createReservationRequestDto);

    // then
    assertThrows(InvalidFormatException.class, creatingReservation);
  }

  @Test
  public void givenCreateReservationRequestDtoWithInvalidFormatDinnerDate_whenCreateNewReservation_thenThrowInvalidFormatException() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withDinnerDate(INVALID_FORMAT_DATE)
                                                                                                      .build();
    doThrow(InvalidFormatException.class).when(dateFormatValidator)
                                         .validateFormat(INVALID_FORMAT_DATE);

    // when
    Executable creatingReservation = () -> reservationResource.createReservation(createReservationRequestDto);

    // then
    assertThrows(InvalidFormatException.class, creatingReservation);
  }

  @Test
  public void givenCreateReservationRequestDtoWithOutOfBoundDinnerDate_whenCreateNewReservation_thenThrowInvalidDinnerDateException() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withDinnerDate(A_OUT_OF_BOUND_DINNER_DATE)
                                                                                                      .build();
    InvalidDinnerDateException invalidDinnerDateException = new InvalidDinnerDateException(START_DATE,
                                                                                           END_DATE);
    given(restaurantService.makeReservation(createReservationRequestDto)).willThrow(invalidDinnerDateException);

    // when
    Executable creatingReservation = () -> reservationResource.createReservation(createReservationRequestDto);

    // then
    assertThrows(InvalidDinnerDateException.class, creatingReservation);
  }

  @Test
  public void givenCreateReservationRequestDtoWithMissingReservationDate_whenCreateNewReservation_thenThrowInvalidFormatException() {
    // given
    ReservationDetailsDto reservationDetailsDto = new ReservationDetailsDtoBuilder().withReservationDate(null)
                                                                                    .build();
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withReservationDetails(reservationDetailsDto)
                                                                                                      .build();
    InvalidFormatException invalidReservationDateFormatException = new InvalidFormatException();
    given(restaurantService.makeReservation(createReservationRequestDto)).willThrow(invalidReservationDateFormatException);

    // when
    Executable creatingReservation = () -> reservationResource.createReservation(createReservationRequestDto);

    // then
    assertThrows(InvalidFormatException.class, creatingReservation);
  }

  @Test
  public void givenCreateReservationRequestDtoWithInvalidFormatReservationDate_whenCreateNewReservation_thenThrowInvalidFormatException() {
    // given
    ReservationDetailsDto reservationDetailsDto = new ReservationDetailsDtoBuilder().withReservationDate(INVALID_FORMAT_DATE)
                                                                                    .build();
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withReservationDetails(reservationDetailsDto)
                                                                                                      .build();
    InvalidFormatException invalidReservationDateFormatException = new InvalidFormatException();
    given(restaurantService.makeReservation(createReservationRequestDto)).willThrow(invalidReservationDateFormatException);

    // when
    Executable creatingReservation = () -> reservationResource.createReservation(createReservationRequestDto);

    // then
    assertThrows(InvalidFormatException.class, creatingReservation);
  }

  @Test
  public void givenCreateReservationRequestDtoWithOutOfBoundReservationDate_whenCreateNewReservation_thenThrowInvalidReservationDateException() {
    // given
    ReservationDetailsDto reservationDetailsDto = new ReservationDetailsDtoBuilder().withReservationDate(A_OUT_OF_BOUND_RESERVATION_DATE)
                                                                                    .build();
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withReservationDetails(reservationDetailsDto)
                                                                                                      .build();
    InvalidReservationDateException invalidReservationDateException = new InvalidReservationDateException(START_DATE,
                                                                                                          END_DATE);
    given(restaurantService.makeReservation(createReservationRequestDto)).willThrow(invalidReservationDateException);

    // when
    Executable creatingReservation = () -> reservationResource.createReservation(createReservationRequestDto);

    // then
    assertThrows(InvalidReservationDateException.class, creatingReservation);
  }

  @Test
  public void whenGetReservation_thenReturnReservationResponse() {
    // given
    ReservationDto expectedReservationDto = new ReservationDtoBuilder().withAnyCustomers().build();
    given(restaurantService.getReservationFromRestaurant(any())).willReturn(expectedReservationDto);

    // when
    Response response = reservationResource.getReservation(AN_ID);

    // then
    assertThat(response.getEntity()).isEqualTo(expectedReservationDto);
  }
}
