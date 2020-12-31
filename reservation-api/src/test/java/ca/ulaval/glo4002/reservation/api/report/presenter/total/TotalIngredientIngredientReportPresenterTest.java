package ca.ulaval.glo4002.reservation.api.report.presenter.total;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;

@ExtendWith(MockitoExtension.class)
public class TotalIngredientIngredientReportPresenterTest {
  @Mock
  private IngredientReport ingredientReport;

  @Mock
  private TotalReportDtoFactory totalReportDtoFactory;

  private TotalIngredientReportPresenter totalReportPresenter;

  @BeforeEach
  public void setUp() {
    totalReportPresenter = new TotalIngredientReportPresenter(totalReportDtoFactory);
  }

  @Test
  public void givenReport_whenPresentReport_thenTotalReportDtoIsCreated() {
    // when
    totalReportPresenter.presentReport(ingredientReport);

    // then
    verify(totalReportDtoFactory).create(ingredientReport);
  }
}
