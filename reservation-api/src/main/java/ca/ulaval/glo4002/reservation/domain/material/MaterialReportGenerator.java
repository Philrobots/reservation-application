package ca.ulaval.glo4002.reservation.domain.material;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import ca.ulaval.glo4002.reservation.domain.report.ReportPeriod;

public class MaterialReportGenerator {
  private static final Map<Material, BigDecimal> INITIAL_DISHES_QUANTITIES = new HashMap<>();
  private final CleanMaterialPriceCalculator cleanMaterialPriceCalculator;
  private final MaterialToBuyPriceCalculator materialToBuyPriceCalculator;

  public MaterialReportGenerator(CleanMaterialPriceCalculator cleanMaterialPriceCalculator,
                                 MaterialToBuyPriceCalculator materialToBuyPriceCalculator)
  {
    this.cleanMaterialPriceCalculator = cleanMaterialPriceCalculator;
    this.materialToBuyPriceCalculator = materialToBuyPriceCalculator;
    initializeDishesQuantities();
  }

  private void initializeDishesQuantities() {
    for (Material material : Material.values()) {
      INITIAL_DISHES_QUANTITIES.put(material, BigDecimal.ZERO);
    }
  }

  public MaterialReport generateReport(Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities,
                                       ReportPeriod reportPeriod)
  {
    List<MaterialReportInformation> allMaterialReportInformationForReportPeriod = generateListOfMaterialReportInformation(dailyDishesQuantities);
    if (isFirstDateOfListOfMaterialReportInformationBeforeReportPeriod(reportPeriod,
                                                                       allMaterialReportInformationForReportPeriod))
    {
      allMaterialReportInformationForReportPeriod.remove(0);
    }
    return new MaterialReport(allMaterialReportInformationForReportPeriod);
  }

  public MaterialReport generateTotalMaterialReport(Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities) {
    List<MaterialReportInformation> materialReportInformations = generateListOfMaterialReportInformation(dailyDishesQuantities);
    return new MaterialReport(materialReportInformations);
  }

  private boolean isFirstDateOfListOfMaterialReportInformationBeforeReportPeriod(ReportPeriod reportPeriod,
                                                                                 List<MaterialReportInformation> listOfMaterialReportInformation)
  {
    if (listOfMaterialReportInformation.isEmpty()) {
      return false;
    }
    return listOfMaterialReportInformation.get(0).getDate().isBefore(reportPeriod.getStartDate());
  }

  private List<MaterialReportInformation> generateListOfMaterialReportInformation(Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities) {
    List<MaterialReportInformation> listOfMaterialReportInformation = new ArrayList<>();
    Map<Material, BigDecimal> availableDishes = new HashMap<>(INITIAL_DISHES_QUANTITIES);
    List<LocalDate> dates = new ArrayList<>(dailyDishesQuantities.keySet());
    Collections.sort(dates);
    for (LocalDate date : dates) {
      Map<Material, BigDecimal> cleanedDishes = new HashMap<>();
      Map<Material, BigDecimal> boughtDishes = new HashMap<>();
      for (Material material : Material.values()) {
        BigDecimal availableMaterial = availableDishes.get(material);
        BigDecimal materialToClean = calculateMaterialToClean(dailyDishesQuantities,
                                                              date,
                                                              material,
                                                              availableMaterial);
        BigDecimal materialToBuy = calculateMaterialToBuy(dailyDishesQuantities,
                                                          date,
                                                          material,
                                                          materialToClean);
        availableDishes.put(material,
                            dailyDishesQuantities.get(date).getDishesQuantity().get(material));
        cleanedDishes.put(material, materialToClean);
        boughtDishes.put(material, materialToBuy);
      }
      BigDecimal totalPrice = calculateTotalPrice(cleanedDishes, boughtDishes);
      MaterialReportInformation dailyMaterialReportInformation = new MaterialReportInformation(date,
                                                                                               cleanedDishes,
                                                                                               boughtDishes,
                                                                                               totalPrice);
      listOfMaterialReportInformation.add(dailyMaterialReportInformation);
    }
    return listOfMaterialReportInformation;
  }

  private BigDecimal calculateMaterialToClean(Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities,
                                              LocalDate date,
                                              Material material,
                                              BigDecimal availableMaterial)
  {
    return availableMaterial.min(dailyDishesQuantities.get(date).getDishesQuantity().get(material));
  }

  private BigDecimal calculateMaterialToBuy(Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities,
                                            LocalDate date,
                                            Material material,
                                            BigDecimal materialToClean)
  {
    return dailyDishesQuantities.get(date)
                                .getDishesQuantity()
                                .get(material)
                                .subtract(materialToClean);
  }

  private BigDecimal calculateTotalPrice(Map<Material, BigDecimal> cleanedDishes,
                                         Map<Material, BigDecimal> boughtDishes)
  {
    BigDecimal boughtPrice = materialToBuyPriceCalculator.calculateBuyPrice(boughtDishes);
    BigDecimal cleanPrice = cleanMaterialPriceCalculator.calculateCleaningPrice(cleanedDishes);
    return boughtPrice.add(cleanPrice);
  }
}
