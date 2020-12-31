package ca.ulaval.glo4002.reservation.domain.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;

import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import ca.ulaval.glo4002.reservation.domain.reservation.table.TableFactory;
import ca.ulaval.glo4002.reservation.service.reservation.dto.TableDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.ReservationRequest;
import ca.ulaval.glo4002.reservation.domain.date.*;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningEvent;

@ExtendWith(MockitoExtension.class)
public class ReservationFactoryTest {
  private static final LocalDateTime DINNER_DATE = LocalDateTime.of(2150, 1, 1, 1, 1);
  private static final LocalDateTime RESERVATION_DATE = LocalDateTime.of(2150, 7, 8, 4, 4);

  @Mock
  private DateFactory dateFactory;

  @Mock
  private ReservationRequest reservationRequest;

  @Mock
  private HoppeningEvent hoppeningEvent;

  @Mock
  private DinnerDate dinnerDate;

  @Mock
  private ReservationDate reservationDate;

  @Mock
  private List<TableDto> tableDtos;

  @Mock
  private List<Table> tables;

  @Mock
  private Period dinnerDinerPeriod;

  @Mock
  TableFactory tableFactory;

  @Mock
  private DinerPeriod reservationDinerPeriod;

  private ReservationFactory reservationFactory;

  @BeforeEach
  public void setUpReservationFactory() {
    reservationFactory = new ReservationFactory(dateFactory, tableFactory);
  }

  @Test
  public void whenCreate_thenDinnerDateIsCreated() {
    // when
    reservationFactory.create(reservationRequest, hoppeningEvent);

    // then
    verify(dateFactory).createDinnerDate(reservationRequest.getDinnerDate(),
                                     hoppeningEvent.getDinnerDinerPeriod());
  }

  @Test
  public void whenCreate_thenReservationDateIsCreated() {
    // when
    reservationFactory.create(reservationRequest, hoppeningEvent);

    // then
    verify(dateFactory).createReservationDate(reservationRequest.getReservationDate(),
                                          hoppeningEvent.getReservationDinerPeriod());
  }


  @Test
  public void whenCreate_thenReservationIsCorrectlyCreated() {
    // given
    setUpHoppeningEvent();
    setUpReservationRequest();
    given(dinnerDate.getLocalDateTime()).willReturn(DINNER_DATE);
    given(reservationDate.getLocalDateTime()).willReturn(RESERVATION_DATE);
    given(dateFactory.createDinnerDate(DINNER_DATE.toString(), dinnerDinerPeriod)).willReturn(dinnerDate);
    given(dateFactory.createReservationDate(RESERVATION_DATE.toString(),
            reservationDinerPeriod)).willReturn(reservationDate);
    given(tableFactory.createTables(tableDtos)).willReturn(tables);

    // when
    Reservation reservation = reservationFactory.create(reservationRequest, hoppeningEvent);

    // then
    assertThat(reservation.getDinnerDate()).isEqualTo(DINNER_DATE);
    assertThat(reservation.getTables()).isEqualTo(tables);
    assertThat(reservation.getReservationDate()).isEqualTo(RESERVATION_DATE);
  }

  private void setUpHoppeningEvent() {
    given(hoppeningEvent.getDinnerDinerPeriod()).willReturn(dinnerDinerPeriod);
    given(hoppeningEvent.getReservationDinerPeriod()).willReturn(reservationDinerPeriod);
  }

  private void setUpReservationRequest() {

    given(reservationRequest.getDinnerDate()).willReturn(DINNER_DATE.toString());
    given(reservationRequest.getReservationDate()).willReturn(RESERVATION_DATE.toString());
    given(reservationRequest.getTables()).willReturn(tableDtos);
  }
}
