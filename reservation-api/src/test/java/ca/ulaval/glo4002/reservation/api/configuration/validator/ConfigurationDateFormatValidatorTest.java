package ca.ulaval.glo4002.reservation.api.configuration.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import ca.ulaval.glo4002.reservation.api.configuration.exception.InvalidDateException;

public class ConfigurationDateFormatValidatorTest {
  private static final String DATE_REGEX = "[0-9]{4}[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";

  private static final String A_VALID_DATE = "2150-07-20";
  private static final String AN_INVALID_DATE = "2150-0000-1234";

  private ConfigurationDateFormatValidator configurationDateFormatValidator;

  @BeforeEach
  public void setUpConfigurationDateFormatValidator() {
    configurationDateFormatValidator = new ConfigurationDateFormatValidator(DATE_REGEX);
  }

  @Test
  public void whenValidateFormat_thenDateIsValidated() {
    // when
    Executable validatingDates = () -> configurationDateFormatValidator.validateFormat(Collections.singletonList(A_VALID_DATE));

    // then
    assertDoesNotThrow(validatingDates);
  }

  @Test
  public void givenAnInvalidDate_whenValidateFormat_thenThrowInvalidDateException() {
    // when
    Executable validatingDates = () -> configurationDateFormatValidator.validateFormat(Collections.singletonList(AN_INVALID_DATE));

    // then
    assertThrows(InvalidDateException.class, validatingDates);
  }

}
