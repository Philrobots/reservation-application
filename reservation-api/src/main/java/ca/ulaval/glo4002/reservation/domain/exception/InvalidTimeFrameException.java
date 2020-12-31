package ca.ulaval.glo4002.reservation.domain.exception;

public class InvalidTimeFrameException extends ConfigurationException {
  private static final String ERROR_CODE = "INVALID_TIME_FRAMES";
  private static final String ERROR_DESCRIPTION = "Invalid time frames, please use better ones.";

  public InvalidTimeFrameException() {
    super(ERROR_CODE, ERROR_DESCRIPTION);
  }
}