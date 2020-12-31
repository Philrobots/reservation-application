package ca.ulaval.glo4002.reservation.domain.date;

import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidDinnerDateException;
import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidReservationDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DateFactoryTest {
    private static final String A_DATE = "2150-07-30T22:59:59.000Z";
    private static final LocalDateTime A_DATE_TO_LOCAL_DATE_TIME = LocalDateTime.of(2150, 7, 30, 22,
            59,
            59,
            0);
    private static final LocalDate START_DATE = LocalDate.of(2150, 7, 20);
    private static final LocalDate END_DATE = LocalDate.of(2150, 7, 30);

    @Mock
    private DinerPeriod dinerPeriod;

    private DateFactory dateFactory;

    @BeforeEach
    public void setUpDinnerDateFactory() {
        dateFactory = new DateFactory();
    }

    @Test
    public void givenADateInPeriod_whenCreate_thenDinnerDateIsCreated() {
        // given
        given(dinerPeriod.isWithinPeriod(A_DATE_TO_LOCAL_DATE_TIME.toLocalDate())).willReturn(true);

        // when
        Date dinnerDate = dateFactory.createDinnerDate(A_DATE, dinerPeriod);

        // then
        assertThat(dinnerDate.getLocalDateTime()).isEqualTo(A_DATE_TO_LOCAL_DATE_TIME);
    }

    @Test
    public void givenADateNotInPeriod_whenCreate_thenThrowInvalidDinnerDateException() {
        // given
        given(dinerPeriod.isWithinPeriod(A_DATE_TO_LOCAL_DATE_TIME.toLocalDate())).willReturn(false);
        given(dinerPeriod.getStartDate()).willReturn(START_DATE);
        given(dinerPeriod.getEndDate()).willReturn(END_DATE);
        // when
        Executable validatingDateRange = () -> dateFactory.createDinnerDate(A_DATE, dinerPeriod);

        // then
        assertThrows(InvalidDinnerDateException.class, validatingDateRange);
    }

    @Test
    public void givenADateInPeriod_whenCreate_thenReservationDateIsCreated() {
        // given
        given(dinerPeriod.isWithinPeriod(A_DATE_TO_LOCAL_DATE_TIME.toLocalDate())).willReturn(true);

        // when
        ReservationDate reservationDate = dateFactory.createReservationDate(A_DATE, dinerPeriod);

        // then
        assertThat(reservationDate.getLocalDateTime()).isEqualTo(A_DATE_TO_LOCAL_DATE_TIME);
    }

    @Test
    public void givenADateNotInPeriod_whenCreate_thenThrowInvalidReservationDateException() {
        // given
        given(dinerPeriod.isWithinPeriod(A_DATE_TO_LOCAL_DATE_TIME.toLocalDate())).willReturn(false);
        given(dinerPeriod.getStartDate()).willReturn(START_DATE);
        given(dinerPeriod.getEndDate()).willReturn(END_DATE);

        // when
        Executable validatingDateRange = () -> dateFactory.createReservationDate(A_DATE, dinerPeriod);

        // then
        assertThrows(InvalidReservationDateException.class, validatingDateRange);
    }
}
