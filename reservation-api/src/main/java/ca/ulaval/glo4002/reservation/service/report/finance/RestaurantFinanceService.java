package ca.ulaval.glo4002.reservation.service.report.finance;

import ca.ulaval.glo4002.reservation.domain.Restaurant;
import ca.ulaval.glo4002.reservation.domain.RestaurantFinance;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.inventory.IngredientQuantityRepository;
import ca.ulaval.glo4002.reservation.domain.material.DailyDishesQuantity;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReport;
import ca.ulaval.glo4002.reservation.domain.material.MaterialReportGenerator;
import ca.ulaval.glo4002.reservation.domain.report.IngredientPriceRepository;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportGenerator;
import ca.ulaval.glo4002.reservation.infra.inmemory.InMemoryIngredientQuantityRepository;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;
import ca.ulaval.glo4002.reservation.service.report.chef.ChefReportService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RestaurantFinanceService implements FinanceService {

    private final IngredientQuantityRepository ingredientQuantityRepository;
    private final IngredientPriceRepository ingredientPriceRepository;
    private final IngredientReportGenerator ingredientReportGenerator;
    private final Restaurant restaurant;
    private final MaterialReportGenerator materialReportGenerator;
    private final ChefReportService chefReportService;

    public RestaurantFinanceService(InMemoryIngredientQuantityRepository ingredientQuantityRepository,
                                    IngredientPriceRepository ingredientPriceRepository,
                                    IngredientReportGenerator ingredientReportGenerator,
                                    Restaurant restaurant,
                                    MaterialReportGenerator materialReportGenerator,
                                    ChefReportService chefReportService)
    {
        this.ingredientQuantityRepository = ingredientQuantityRepository;
        this.ingredientPriceRepository = ingredientPriceRepository;
        this.ingredientReportGenerator = ingredientReportGenerator;
        this.restaurant = restaurant;
        this.materialReportGenerator = materialReportGenerator;
        this.chefReportService = chefReportService;
    }


    public RestaurantFinance calculateRestaurantFinance() {
        return new RestaurantFinance(restaurant.getRestaurantIncome(), chefReportService.getTotalChefFees(), getIngredientsCost(), getMaterialCost());
    }

    private BigDecimal getMaterialCost() {
        Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = restaurant.getRestaurantTotalDishes();
        MaterialReport materialReport = materialReportGenerator.generateTotalMaterialReport(dailyDishesQuantity);
        return materialReport.getMaterialCost();
    }

    private BigDecimal getIngredientsCost() {
        List<IngredientPriceDto> ingredientPrices = ingredientPriceRepository.getIngredientsPrice();
        Map<LocalDate, Map<IngredientName, BigDecimal>> dateToIngredientQuantities = ingredientQuantityRepository.getAllIngredients();
        IngredientReport ingredientReport = ingredientReportGenerator.generateReport(ingredientPrices, dateToIngredientQuantities);
        return ingredientReport.calculateTotalPriceForEntireReport();
    }
}
