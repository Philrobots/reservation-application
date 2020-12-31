package ca.ulaval.glo4002.reservation.api.report.presenter.material;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.report.dto.MaterialReportDayDto;
import ca.ulaval.glo4002.reservation.api.report.dto.MaterialReportDto;
import ca.ulaval.glo4002.reservation.domain.material.Material;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReport;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReportInformation;

@ExtendWith(MockitoExtension.class)
class MaterialReportDtoFactoryTest {
  private final LocalDate CURRENT_TIME = LocalDate.now();
  private final LocalDate A_PAST_DATE = LocalDate.of(2020, 8, 18);
  private final BigDecimal TOTAL_PRICE = BigDecimal.TEN;
  private final BigDecimal A_DISHES_QUANTITY = BigDecimal.valueOf(6);

  @Mock
  private MaterialReportDtoFactory materialReportDtoFactory;

  @BeforeEach
  void setUpMaterialReportDtoFactory() {
    materialReportDtoFactory = new MaterialReportDtoFactory();
  }

  @Test
  public void whenCreate_thenShouldReturnValidDto() {
    // given
    MaterialReport materialReport = givenMaterialReport();
    MaterialReportDto expectedMaterialReportDto = givenExpectedMaterialReportDto();

    // when
    MaterialReportDto actualMaterialReportDto = materialReportDtoFactory.create(materialReport);

    // then
    assertThat(actualMaterialReportDto.getDates()
                                      .get(0)
                                      .getBought()).isEqualTo(expectedMaterialReportDto.getDates()
                                                                                       .get(0)
                                                                                       .getBought());
    assertThat(actualMaterialReportDto.getDates()
                                      .get(0)
                                      .getCleaned()).isEqualTo(expectedMaterialReportDto.getDates()
                                                                                        .get(0)
                                                                                        .getCleaned());
    assertThat(actualMaterialReportDto.getDates()
                                      .get(0)
                                      .getDate()).isEqualTo(expectedMaterialReportDto.getDates()
                                                                                     .get(0)
                                                                                     .getDate());
    assertThat(actualMaterialReportDto.getDates()
                                      .get(0)
                                      .getTotalPrice()).isEqualTo(expectedMaterialReportDto.getDates()
                                                                                           .get(0)
                                                                                           .getTotalPrice());
  }

  @Test
  public void givenMultipleDates_whenCreate_thenShouldReturnDatesInChronologicalOrder() {
    // given
    MaterialReport materialReport = givenMaterialReportWithManyDays();
    MaterialReportDto expectedMaterialReportDto = givenExpectedMaterialReportDtoWithManyDates();

    // when
    MaterialReportDto actualMaterialReportDto = materialReportDtoFactory.create(materialReport);

    // then
    assertThat(actualMaterialReportDto.getDates()
                                      .get(0)
                                      .getDate()).isEqualTo(expectedMaterialReportDto.getDates()
                                                                                     .get(0)
                                                                                     .getDate());
    assertThat(actualMaterialReportDto.getDates()
                                      .get(1)
                                      .getDate()).isEqualTo(expectedMaterialReportDto.getDates()
                                                                                     .get(1)
                                                                                     .getDate());
  }

  private Map<String, BigDecimal> givenCleanedAndBoughtMaterialQuantitiesStringify() {
    Map<String, BigDecimal> dishesToQuantity = new HashMap<>();
    dishesToQuantity.put("knife", A_DISHES_QUANTITY);
    dishesToQuantity.put("fork", A_DISHES_QUANTITY);
    dishesToQuantity.put("spoon", A_DISHES_QUANTITY);
    dishesToQuantity.put("bowl", A_DISHES_QUANTITY);
    dishesToQuantity.put("plate", A_DISHES_QUANTITY);
    return dishesToQuantity;
  }

  private MaterialReportDto givenExpectedMaterialReportDto() {
    ArrayList<MaterialReportDayDto> materialReportDayDtos = new ArrayList<>();
    MaterialReportDayDto day = new MaterialReportDayDto(CURRENT_TIME.toString(),
                                                        givenCleanedAndBoughtMaterialQuantitiesStringify(),
                                                        givenCleanedAndBoughtMaterialQuantitiesStringify(),
                                                        TOTAL_PRICE);
    materialReportDayDtos.add(day);
    return new MaterialReportDto(materialReportDayDtos);
  }

  private MaterialReportDto givenExpectedMaterialReportDtoWithManyDates() {
    List<MaterialReportDayDto> materialReportDayDtos = new ArrayList<>();
    MaterialReportDayDto aMaterialReportDayDto = new MaterialReportDayDto(A_PAST_DATE.toString(),
                                                                          givenCleanedAndBoughtMaterialQuantitiesStringify(),
                                                                          givenCleanedAndBoughtMaterialQuantitiesStringify(),
                                                                          TOTAL_PRICE);
    MaterialReportDayDto anotherMaterialReportDayDto = new MaterialReportDayDto(CURRENT_TIME.toString(),
                                                                                givenCleanedAndBoughtMaterialQuantitiesStringify(),
                                                                                givenCleanedAndBoughtMaterialQuantitiesStringify(),
                                                                                TOTAL_PRICE);
    materialReportDayDtos.add(aMaterialReportDayDto);
    materialReportDayDtos.add(anotherMaterialReportDayDto);
    return new MaterialReportDto(materialReportDayDtos);
  }

  private Map<Material, BigDecimal> givenCleanedAndBoughtMaterialQuantities() {
    Map<Material, BigDecimal> map = new HashMap<>();
    map.put(Material.KNIFE, A_DISHES_QUANTITY);
    map.put(Material.FORK, A_DISHES_QUANTITY);
    map.put(Material.SPOON, A_DISHES_QUANTITY);
    map.put(Material.BOWL, A_DISHES_QUANTITY);
    map.put(Material.PLATE, A_DISHES_QUANTITY);
    return map;
  }

  private MaterialReport givenMaterialReport() {
    List<MaterialReportInformation> materialReportInfos = new ArrayList<>();
    MaterialReportInformation info = new MaterialReportInformation(CURRENT_TIME,
                                                                   givenCleanedAndBoughtMaterialQuantities(),
                                                                   givenCleanedAndBoughtMaterialQuantities(),
                                                                   TOTAL_PRICE);
    materialReportInfos.add(info);
    return new MaterialReport(materialReportInfos);
  }

  private MaterialReport givenMaterialReportWithManyDays() {
    List<MaterialReportInformation> materialReportInfos = new ArrayList<>();
    MaterialReportInformation aMaterialReportInformation = new MaterialReportInformation(CURRENT_TIME,
                                                                                         givenCleanedAndBoughtMaterialQuantities(),
                                                                                         givenCleanedAndBoughtMaterialQuantities(),
                                                                                         TOTAL_PRICE);
    MaterialReportInformation anotherMaterialReportInformation = new MaterialReportInformation(A_PAST_DATE,
                                                                                               givenCleanedAndBoughtMaterialQuantities(),
                                                                                               givenCleanedAndBoughtMaterialQuantities(),
                                                                                               TOTAL_PRICE);
    materialReportInfos.add(aMaterialReportInformation);
    materialReportInfos.add(anotherMaterialReportInformation);
    return new MaterialReport(materialReportInfos);
  }
}
