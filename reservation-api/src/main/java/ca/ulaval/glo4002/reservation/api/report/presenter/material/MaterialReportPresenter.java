package ca.ulaval.glo4002.reservation.api.report.presenter.material;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.reservation.api.report.dto.MaterialReportDto;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReport;

public class MaterialReportPresenter {
  private final MaterialReportDtoFactory materialReportDtoFactory;

  public MaterialReportPresenter(MaterialReportDtoFactory materialReportDtoFactory) {
    this.materialReportDtoFactory = materialReportDtoFactory;
  }

  public Response presentReport(MaterialReport materialReport) {
    MaterialReportDto materialReportDto = materialReportDtoFactory.create(materialReport);
    return Response.ok().entity(materialReportDto).build();
  }
}
