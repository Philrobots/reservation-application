package ca.ulaval.glo4002.reservation.api.reservation.validator;

import java.util.regex.Pattern;

import ca.ulaval.glo4002.reservation.api.reservation.exception.InvalidFormatException;

public class DateFormatValidator {
  private final Pattern datePattern;

  public DateFormatValidator(String dateRegex) {
    this.datePattern = Pattern.compile(dateRegex);
  }

  public void validateFormat(String date) {
    if (!datePattern.matcher(date).find()) {
      throw new InvalidFormatException();
    }
  }
}
