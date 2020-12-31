package ca.ulaval.glo4002.reservation.infra.inmemory;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.service.reservation.exception.ReservationNotFoundException;

@ExtendWith(MockitoExtension.class)
public class InMemoryReservationRepositoryTest {
  private static final LocalDateTime A_DATE = LocalDateTime.of(2020, 7, 20, 23, 23);
  private static final LocalDateTime ANOTHER_DATE = LocalDateTime.of(2050, 1, 14, 1, 4);
  private static final BigDecimal A_PRICE = BigDecimal.valueOf(75432);
  private static final BigDecimal ANOTHER_PRICE = BigDecimal.valueOf(32456);

  @Mock
  private Reservation aReservation;

  @Mock
  private Reservation anotherReservation;

  @Mock
  private ReservationNumber reservationId;

  private InMemoryReservationRepository reservationRepository;

  @BeforeEach
  public void setUp() {
    reservationRepository = new InMemoryReservationRepository();
  }

  @Test
  public void givenAReservation_whenSaveReservation_thenReturnReservationId() {
    // given
    given(aReservation.getReservationId()).willReturn(reservationId);

    // when
    ReservationNumber expectedReservationId = reservationRepository.saveReservation(aReservation);

    // then
    assertThat(expectedReservationId).isEqualTo(reservationId);
  }

  @Test
  public void givenAReservation_whenGetReservationById_thenReturnProperReservation() {
    // given
    given(aReservation.getReservationId()).willReturn(reservationId);
    reservationRepository.saveReservation(aReservation);

    // when
    Reservation actualReservation = reservationRepository.getReservationById(reservationId);

    // then
    assertThat(actualReservation).isEqualTo(aReservation);
  }

  @Test
  public void givenNoReservationForDate_whenGetReservationByDate_thenNoReservationsAreReturned() {
    // when
    List<Reservation> reservations = reservationRepository.getReservationsByDate(ANOTHER_DATE);

    // then
    assertThat(reservations).isEmpty();
  }

  @Test
  public void givenReservationAtDifferentDates_whenGetReservationByDate_thenReturnTheReservationsAtTheSpecifiedDate() {
    // given
    given(aReservation.getDinnerDate()).willReturn(A_DATE);
    given(anotherReservation.getDinnerDate()).willReturn(ANOTHER_DATE);
    reservationRepository.saveReservation(aReservation);
    reservationRepository.saveReservation(anotherReservation);

    // when
    List<Reservation> reservations = reservationRepository.getReservationsByDate(A_DATE);

    // then
    assertThat(reservations).contains(aReservation);
    assertThat(reservations).doesNotContain(anotherReservation);
  }

  @Test
  public void givenReservationAtTheSameDate_whenGetReservationByDate_thenReturnTheReservationsAtTheSpecifiedDate() {
    // given
    given(aReservation.getDinnerDate()).willReturn(A_DATE);
    given(anotherReservation.getDinnerDate()).willReturn(A_DATE);
    reservationRepository.saveReservation(aReservation);
    reservationRepository.saveReservation(anotherReservation);

    // when
    List<Reservation> reservations = reservationRepository.getReservationsByDate(A_DATE);

    // then
    assertThat(reservations).contains(aReservation);
    assertThat(reservations).contains(anotherReservation);
  }

  @Test
  public void givenNotExistingReservation_whenGetReservationById_thenThrowNonExistingReservationException() {
    // when
    Executable gettingReservationById = () -> reservationRepository.getReservationById(reservationId);

    // then
    assertThrows(ReservationNotFoundException.class, gettingReservationById);
  }

  @Test
  public void givenAListOfReservation_whenGetTotalReservationFees_thenShouldReturnTheRightTotal() {
    // given
    given(aReservation.getReservationFees()).willReturn(A_PRICE);
    given(anotherReservation.getReservationFees()).willReturn(ANOTHER_PRICE);
    reservationRepository.saveReservation(aReservation);
    reservationRepository.saveReservation(anotherReservation);

    // when
    BigDecimal actualPrice = reservationRepository.getTotalReservationIncome();

    // then
    BigDecimal expectedTotalPrice = A_PRICE.add(ANOTHER_PRICE);
    assertThat(actualPrice).isEqualTo(expectedTotalPrice);


  }
}
