package ca.ulaval.glo4002.reservation.api.report.presenter.material;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.material.MaterialReport;

@ExtendWith(MockitoExtension.class)
public class MaterialReportPresenterTest {
  @Mock
  private MaterialReport materialReport;

  @Mock
  private MaterialReportDtoFactory materialReportDtoFactory;

  private MaterialReportPresenter materialReportPresenter;

  @BeforeEach
  public void setUp() {
    materialReportPresenter = new MaterialReportPresenter(materialReportDtoFactory);
  }

  @Test
  public void givenReport_whenPresentReport_thenTotalReportDtoIsCreated() {
    // when
    materialReportPresenter.presentReport(materialReport);

    // then
    verify(materialReportDtoFactory).create(materialReport);
  }
}
