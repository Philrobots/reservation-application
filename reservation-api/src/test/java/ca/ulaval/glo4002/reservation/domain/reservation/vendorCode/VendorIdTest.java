package ca.ulaval.glo4002.reservation.domain.reservation.vendorCode;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VendorIdTest {

    private static final String VENDOR_CODE_TEAM = "TEAM";
    private static final String VENDOR_CODE_WITH_LINE = "TEAM-";

    private ReservationNumber reservationId;

    private String reservationCode;

    @BeforeEach
    public void setUpReservationId() {
        reservationId = new VendorId(VENDOR_CODE_TEAM);
        reservationCode = reservationId.getReservationNumber();
    }

    @Test
     public void whenGetCode_thenCodeShouldStartWithTEAMAndLine() {
        // then
        assertThat(reservationCode.startsWith(VENDOR_CODE_WITH_LINE)).isTrue();
     }

     @Test
    public void whenGetCode_theCodeShouldOnlyContainsNumberAfterLine() {
        // then
         String code = reservationCode.substring(5);
         boolean isOnlyNumber = code.matches("[0-9]+");
        assertThat(isOnlyNumber).isTrue();

     }
}
