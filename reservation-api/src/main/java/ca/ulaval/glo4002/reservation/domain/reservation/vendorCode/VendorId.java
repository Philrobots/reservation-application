package ca.ulaval.glo4002.reservation.domain.reservation.vendorCode;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class VendorId implements ReservationNumber {
  private final int lowerNumber = 999;
  private final int upperNumber = 9999;
  private String id;

  public VendorId(String vendorCode) {
    id = vendorCode + "-" + getVendorCodeNumber();
  }

  public VendorId() {}

  private String getVendorCodeNumber() {
    return String.valueOf(ThreadLocalRandom.current().ints(lowerNumber, upperNumber).distinct().limit(6).sum());
  }

  public void setReservationNumber(String id) {
    this.id = id;
  }

  public String getReservationNumber() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof VendorId)) {
      return false;
    }
    VendorId vendorId = (VendorId) o;
    return Objects.equals(id, vendorId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
