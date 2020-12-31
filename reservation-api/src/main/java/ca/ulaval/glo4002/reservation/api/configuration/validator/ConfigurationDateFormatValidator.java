package ca.ulaval.glo4002.reservation.api.configuration.validator;

import java.util.List;
import java.util.regex.Pattern;

import ca.ulaval.glo4002.reservation.api.configuration.exception.InvalidDateException;

public class ConfigurationDateFormatValidator {
  private final Pattern datePattern;

  public ConfigurationDateFormatValidator(String dateRegex) {
    datePattern = Pattern.compile(dateRegex);
  }

  public void validateFormat(List<String> configurationDates) {
    for (String date : configurationDates) {
      if (!datePattern.matcher(date).find()) {
        throw new InvalidDateException();
      }
    }
  }
}
