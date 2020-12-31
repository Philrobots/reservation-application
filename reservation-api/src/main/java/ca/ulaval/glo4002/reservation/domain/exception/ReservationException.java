package ca.ulaval.glo4002.reservation.domain.exception;

public abstract class ReservationException extends RuntimeException {
  private final String error;
  private final String description;

  public ReservationException(String error, String description) {
    this.error = error;
    this.description = description;
  }

  public String getError() {
    return error;
  }

  public String getDescription() {
    return description;
  }
}
