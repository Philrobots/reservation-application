package ca.ulaval.glo4002.reservation.service.finance;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.reservation.domain.Restaurant;
import ca.ulaval.glo4002.reservation.domain.RestaurantFinance;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.material.DailyDishesQuantity;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReport;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReportGenerator;
import ca.ulaval.glo4002.reservation.domain.report.*;
import ca.ulaval.glo4002.reservation.infra.inmemory.InMemoryIngredientQuantityRepository;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;
import ca.ulaval.glo4002.reservation.service.report.chef.ChefReportService;
import ca.ulaval.glo4002.reservation.service.report.finance.FinanceService;
import ca.ulaval.glo4002.reservation.service.report.finance.RestaurantFinanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RestaurantFinanceServiceTest {

    private static final BigDecimal AN_INCOME = BigDecimal.valueOf(3456);
    private static final BigDecimal A_CHEF_FEES = BigDecimal.valueOf(345546);
    private static final BigDecimal A_MATERIAL_COST = BigDecimal.valueOf(13423);
    private static final BigDecimal A_INGREDIENT_COST = BigDecimal.valueOf(3425);

    @Mock
    private InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository;

    @Mock
    private IngredientPriceRepository ingredientPriceRepository;

    @Mock
    private IngredientReportGenerator ingredientReportGenerator;

    @Mock
    private Restaurant restaurant;

    @Mock
    private MaterialReportGenerator materialReportGenerator;

    @Mock
    private Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities;

    @Mock
    private MaterialReport materialReport;

    @Mock
    private List<IngredientPriceDto> ingredientPrices;

    @Mock
    private IngredientReport ingredientReport;

    @Mock
    private Map<LocalDate, Map<IngredientName, BigDecimal>> dateToIngredientQuantities;

    @Mock
    ChefReportService chefReportService;

    private FinanceService restaurantFinanceService;

    private RestaurantFinance restaurantFinance;

    @BeforeEach
    public void setUpRestaurantFinanceService() {
        restaurantFinanceService = new RestaurantFinanceService(inMemoryIngredientQuantityRepository, ingredientPriceRepository, ingredientReportGenerator,
                restaurant, materialReportGenerator, chefReportService);

            givenRightInformation();

         restaurantFinance = restaurantFinanceService.calculateRestaurantFinance();

    }

    @Test
    public void givenARestaurantIncome_whenCalculateRestaurantFinance_thenShouldBeTheRightIncome() {
        // then
        assertThat(restaurantFinance.getIncome()).isEqualTo(AN_INCOME);
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenChefReportServiceFeesShouldBeCall() {
        // then
        verify(chefReportService).getTotalChefFees();
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenMaterialReportGetMaterialCostShouldBeCall() {
        // then
        verify(materialReport).getMaterialCost();
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenRestaurantGetTotalDishesShouldBeCall() {
        // then
        verify(restaurant).getRestaurantTotalDishes();
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenMaterialReportGeneratorGetTotalMaterialReportShouldBeCall() {
        // then
        verify(materialReportGenerator).generateTotalMaterialReport(dailyDishesQuantities);
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenIngredientPriceRepositoryGetIngredientsPriceShouldBeCall() {
        // then
        verify(ingredientPriceRepository).getIngredientsPrice();
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenIngredientQuantityRepositoryGetAllIngredientShouldBeCall() {
        // then
        verify(inMemoryIngredientQuantityRepository).getAllIngredients();
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenIngredientReportCalculateTotalPriceShouldBeCall() {
        // then
        verify(ingredientReport).calculateTotalPriceForEntireReport();
    }

    @Test
    public void givenTheRightInformation_whenCalculateRestaurantFinance_thenIngredientReportGeneratorGenerateReportShouldBeCall() {
        // then
        verify(ingredientReportGenerator).generateReport(ingredientPrices, dateToIngredientQuantities);
    }


    private void givenRightInformation() {
        given(chefReportService.getTotalChefFees()).willReturn(A_CHEF_FEES);
        given(restaurant.getRestaurantIncome()).willReturn(AN_INCOME);
        given(restaurant.getRestaurantTotalDishes()).willReturn(dailyDishesQuantities);
        given(materialReport.getMaterialCost()).willReturn(A_MATERIAL_COST);
        given(materialReportGenerator.generateTotalMaterialReport(dailyDishesQuantities)).willReturn(materialReport);
        given(ingredientPriceRepository.getIngredientsPrice()).willReturn(ingredientPrices);
        given(inMemoryIngredientQuantityRepository.getAllIngredients()).willReturn(dateToIngredientQuantities);
        given(ingredientReport.calculateTotalPriceForEntireReport()).willReturn(A_INGREDIENT_COST);
        given(ingredientReportGenerator.generateReport(ingredientPrices, dateToIngredientQuantities)).willReturn(ingredientReport);

    }
}
