package ca.ulaval.glo4002.reservation.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class ReservationBookTest {
  private static final LocalDateTime A_DATE = LocalDateTime.of(2020, 7, 20, 23, 23);
  private static final int FOUR_CUSTOMERS = 4;
  private static final int EIGHT_CUSTOMERS = 8;
  private static final int ONE_CUSTOMER = 1;

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private Reservation aReservation;

  @Mock
  private Reservation anotherReservation;

  private ReservationBook reservationBook;

  @BeforeEach
  public void setUpReservationBook() {
    reservationBook = new ReservationBook(reservationRepository);
  }

  @Test
  public void whenRegister_thenReservationIsRegistered() {
    // when
    reservationBook.register(aReservation);

    // then
    verify(reservationRepository).saveReservation(aReservation);
  }

  @Test
  public void givenAReservationWithOneCustomer_whenGetTotalNumberOfCustomerForADay_thenReturnOneCustomer() {
    // given
    given(aReservation.getNumberOfCustomers()).willReturn(ONE_CUSTOMER);
    given(reservationRepository.getReservationsByDate(A_DATE)).willReturn(Collections.singletonList(aReservation));

    // when
    int totalNumberOfReservationForADay = reservationBook.getNumberOfCustomersForADay(A_DATE);

    // then
    assertThat(totalNumberOfReservationForADay).isEqualTo(ONE_CUSTOMER);
  }

  @Test
  public void givenTwoReservationsWithATotalOfEightCustomers_whenGetTotalNumberOfCustomersForADay_thenReturnEightCustomers() {
    // given
    given(aReservation.getNumberOfCustomers()).willReturn(FOUR_CUSTOMERS);
    given(anotherReservation.getNumberOfCustomers()).willReturn(FOUR_CUSTOMERS);
    given(reservationRepository.getReservationsByDate(A_DATE)).willReturn(Arrays.asList(aReservation,
                                                                                        anotherReservation));

    // when
    int totalNumberOfReservationForADay = reservationBook.getNumberOfCustomersForADay(A_DATE);

    // then
    assertThat(totalNumberOfReservationForADay).isEqualTo(EIGHT_CUSTOMERS);
  }
}
