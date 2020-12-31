package ca.ulaval.glo4002.reservation.api.report.presenter.unit;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;

@ExtendWith(MockitoExtension.class)
public class UnitIngredientIngredientReportPresenterTest {
  @Mock
  private IngredientReport ingredientReport;

  @Mock
  private UnitReportDtoFactory unitReportDtoFactory;

  private UnitIngredientReportPresenter unitReportPresenter;

  @BeforeEach
  public void setUp() {
    unitReportPresenter = new UnitIngredientReportPresenter(unitReportDtoFactory);
  }

  @Test
  public void givenReport_whenPresentReport_thenUnitReportDtoIsCreated() {
    // when
    unitReportPresenter.presentReport(ingredientReport);

    // then
    verify(unitReportDtoFactory).create(ingredientReport);
  }
}
