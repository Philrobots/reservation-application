package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.reservation.builder.CustomerDtoBuilder;
import ca.ulaval.glo4002.reservation.api.reservation.dto.CustomerApiDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDto;
import ca.ulaval.glo4002.reservation.domain.builder.CustomerBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.ReservationBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.TableBuilder;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;

@ExtendWith(MockitoExtension.class)
class ReservationAssemblerTest {
  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final LocalDateTime DATE = LocalDateTime.of(2001, 9, 11, 16, 12, 12, 142000000);
  private static final String DATE_STRING = "2001-09-11T16:12:12.142Z";
  private static final String VEGAN_RESTRICTION = "vegan";
  private static final BigDecimal A_ROUNDED_RESERVATION_PRICE = BigDecimal.valueOf(1234.57);
  private static final BigDecimal A_RESERVATION_PRICE = BigDecimal.valueOf(1234.5678);

  @Mock
  private CustomerAssembler customerAssembler;

  @Mock
  private Table aTable;

  private ReservationAssembler reservationAssembler;

  @BeforeEach
  public void setUp() {
    reservationAssembler = new ReservationAssembler(DATE_FORMAT, customerAssembler);
  }

  @Test
  public void whenCreateDtoFromReservation_thenReturnedDtoHasProperDate() {
    // given
    Reservation reservation = new ReservationBuilder().withDinnerDate(DATE).build();

    // when
    ReservationDto reservationDto = reservationAssembler.assembleDtoFromReservation(reservation);

    // then
    assertThat(reservationDto.getDinnerDate()).isEqualTo((DATE_STRING));
  }

  @Test
  public void whenAssembleDtoFromReservation_thenReturnedDtoHasProperRoundedReservationPrice() {
    // given
    given(aTable.getTableReservationFees()).willReturn(A_RESERVATION_PRICE);
    Reservation reservation = new ReservationBuilder().withTable(aTable).build();

    // when
    ReservationDto reservationDto = reservationAssembler.assembleDtoFromReservation(reservation);

    // then
    assertThat(reservationDto.getReservationPrice()).isEqualTo(A_ROUNDED_RESERVATION_PRICE);
  }

  @Test
  public void givenReservationWithMultipleTables_whenCreateDtoFromReservation_thenDtoHasAllCustomers() {
    // given
    Customer customer = new CustomerBuilder().withRestriction(RestrictionType.VEGAN).build();
    Table table = new TableBuilder().withCustomer(customer).build();
    CustomerApiDto customerApiDto = new CustomerDtoBuilder().withRestriction(VEGAN_RESTRICTION)
                                                            .build();
    Reservation reservation = new ReservationBuilder().withTable(table).build();
    given(customerAssembler.assembleDtoFromCustomer(customer)).willReturn(customerApiDto);

    // when
    ReservationDto reservationDto = reservationAssembler.assembleDtoFromReservation(reservation);

    // then
    assertThat(reservationDto.getCustomers()).contains(customerApiDto);
  }
}
