package ca.ulaval.glo4002.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ca.ulaval.glo4002.reservation.api.configuration.ConfigurationResource;
import ca.ulaval.glo4002.reservation.api.configuration.validator.ConfigurationDateFormatValidator;
import ca.ulaval.glo4002.reservation.api.report.IngredientReportPresenterFactory;
import ca.ulaval.glo4002.reservation.api.report.ReportResource;
import ca.ulaval.glo4002.reservation.api.report.assembler.ChefReportDtoAssembler;
import ca.ulaval.glo4002.reservation.api.report.assembler.RestaurantFinanceDtoAssembler;
import ca.ulaval.glo4002.reservation.api.report.presenter.material.MaterialReportDtoFactory;
import ca.ulaval.glo4002.reservation.api.report.presenter.material.MaterialReportPresenter;
import ca.ulaval.glo4002.reservation.api.report.presenter.total.TotalReportDtoFactory;
import ca.ulaval.glo4002.reservation.api.report.presenter.unit.UnitReportDayDtoFactory;
import ca.ulaval.glo4002.reservation.api.report.presenter.unit.UnitReportDtoFactory;
import ca.ulaval.glo4002.reservation.api.report.validator.ReportDateValidator;
import ca.ulaval.glo4002.reservation.api.reservation.ReservationResource;
import ca.ulaval.glo4002.reservation.api.reservation.validator.DateFormatValidator;
import ca.ulaval.glo4002.reservation.domain.date.*;
import ca.ulaval.glo4002.reservation.domain.inventory.IngredientInventory;
import ca.ulaval.glo4002.reservation.domain.inventory.Inventory;
import ca.ulaval.glo4002.reservation.domain.ReservationBook;
import ca.ulaval.glo4002.reservation.domain.Restaurant;
import ca.ulaval.glo4002.reservation.domain.chef.ChefSelector;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.MenuRepository;
import ca.ulaval.glo4002.reservation.domain.fullcourse.stock.Available;
import ca.ulaval.glo4002.reservation.domain.fullcourse.stock.TomatoStock;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningConfigurationRequestFactory;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningEvent;
import ca.ulaval.glo4002.reservation.domain.material.*;
import ca.ulaval.glo4002.reservation.domain.report.*;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefReportGenerator;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.*;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.CustomerFactory;
import ca.ulaval.glo4002.reservation.domain.reservation.ingredient.IngredientCalculator;
import ca.ulaval.glo4002.reservation.domain.reservation.ingredient.ReservationIngredientCalculator;
import ca.ulaval.glo4002.reservation.domain.reservation.table.TableFactory;
import ca.ulaval.glo4002.reservation.infra.inmemory.*;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceHttpRepository;
import ca.ulaval.glo4002.reservation.server.ReservationServer;
import ca.ulaval.glo4002.reservation.service.report.chef.ChefReportService;
import ca.ulaval.glo4002.reservation.service.report.finance.FinanceService;
import ca.ulaval.glo4002.reservation.service.report.chef.ReportService;
import ca.ulaval.glo4002.reservation.service.RestaurantService;
import ca.ulaval.glo4002.reservation.service.report.finance.RestaurantFinanceService;
import ca.ulaval.glo4002.reservation.service.reservation.assembler.*;

public class ReservationContext {
  private static final int PORT = 8181;
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final String DATE_REGEX = "[0-9]{4}[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";
  private static final String DATE_TIME_REGEX = "[0-9]{4}[-][0-9]{2}[-][0-9]{2}[T][0-9]{2}[:][0-9]{2}[:][0-9]{2}[.][0-9]{3}[Z]";
  private static final LocalDate OPENING_DINNER_LOCAL_DATE = LocalDate.of(2150, 7, 20);
  private static final LocalDate CLOSING_DINNER_LOCAL_DATE = LocalDate.of(2150, 7, 30);
  private static final LocalDate OPENING_RESERVATION_LOCAL_DATE = LocalDate.of(2150, 1, 1);
  private static final LocalDate CLOSING_RESERVATION_LOCAL_DATE = LocalDate.of(2150, 7, 16);
  private static final IngredientName TOMATO = IngredientName.TOMATO;
  private static final int DAY_BEFORE_TOMATO_BECOME_AVAILABLE = 5;

  private ReservationServer server;

  public void start() {
    IngredientCalculator ingredientCalculator = createReservationIngredientCalculator();
    InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository = new InMemoryIngredientQuantityRepository();
    ReservationRepository reservationRepository = new InMemoryReservationRepository();
    Set<Available> availableIngredient = createAvailableIngredient();
    AllergiesDetector allergiesDetector = new AllergiesDetector(ingredientCalculator);
    Buffet buffet = new Buffet(new DailyDishesQuantityFactory());
    ChefRepository chefRepository = new InMemoryChefRepository();
    ChefReportService chefReportService = createChefReportService(chefRepository);
    ChefSelector chefSelector = new ChefSelector(chefRepository);
    Restaurant restaurant = createRestaurant(inMemoryIngredientQuantityRepository, reservationRepository, allergiesDetector, buffet,
            chefSelector, availableIngredient, ingredientCalculator);
    IngredientPriceRepository ingredientPriceRepository = new IngredientPriceHttpRepository();
    IngredientPriceCalculatorFactory ingredientPriceCalculatorFactory = new IngredientPriceCalculatorFactory();
    IngredientReportInformationFactory ingredientReportInformationFactory = new IngredientReportInformationFactory();
    DailyIngredientReportInformationFactory dailyIngredientReportInformationFactory = new DailyIngredientReportInformationFactory(ingredientReportInformationFactory);
    IngredientReportFactory ingredientReportFactory = new IngredientReportFactory(dailyIngredientReportInformationFactory);
    IngredientReportGenerator ingredientReportGenerator = new IngredientReportGenerator(ingredientPriceCalculatorFactory, ingredientReportFactory);
    MaterialToBuyPriceCalculator materialToBuyPriceCalculator = new MaterialToBuyPriceCalculator();
    CleanMaterialPriceCalculator cleanMaterialPriceCalculator = new CleanMaterialPriceCalculator();
    MaterialReportGenerator materialReportGenerator = new MaterialReportGenerator(cleanMaterialPriceCalculator, materialToBuyPriceCalculator);


    RestaurantService restaurantService = createReservationService(restaurant);

    FinanceService financeService = createFinanceService(inMemoryIngredientQuantityRepository, ingredientPriceRepository, ingredientReportGenerator,
            restaurant, materialReportGenerator, chefReportService);

    ReportService reportService = createReportService(inMemoryIngredientQuantityRepository, restaurant, ingredientPriceRepository,
            ingredientReportGenerator, materialReportGenerator);

    Object[] resources = createResources(restaurantService, reportService, chefReportService, financeService);

    server = createServer(resources);

    server.start();
  }

  private FinanceService createFinanceService(InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository,
                                              IngredientPriceRepository ingredientPriceRepository,
                                              IngredientReportGenerator ingredientReportGenerator, Restaurant restaurant,
                                              MaterialReportGenerator materialReportGenerator,
                                              ChefReportService chefReportService) {

    return new RestaurantFinanceService(inMemoryIngredientQuantityRepository, ingredientPriceRepository, ingredientReportGenerator,
            restaurant, materialReportGenerator, chefReportService);
  }

  private ChefReportService createChefReportService(ChefRepository chefRepository) {
    ChefReportGenerator chefReportGenerator = new ChefReportGenerator();
    return new ChefReportService(chefReportGenerator, chefRepository);
  }

  private RestaurantService createReservationService(Restaurant restaurant) {
    CustomerAssembler customerAssembler = new CustomerAssembler();
    CustomerDtoAssembler customerDtoAssembler = new CustomerDtoAssembler();
    TableDtoAssembler tableDtoAssembler = new TableDtoAssembler(customerDtoAssembler);
    ReservationRequestAssembler reservationRequestAssembler = new ReservationRequestAssembler(tableDtoAssembler);
    PeriodDtoAssembler eventPeriodDtoAssembler = new PeriodDtoAssembler();
    HoppeningConfigurationRequestFactory hoppeningConfigurationRequestFactory = new HoppeningConfigurationRequestFactory();
    ConfigurationRequestAssembler configurationRequestAssembler = new ConfigurationRequestAssembler(eventPeriodDtoAssembler,
                                                                                                    hoppeningConfigurationRequestFactory);

    return new RestaurantService(new ReservationAssembler(DATE_TIME_FORMAT, customerAssembler),
                                 reservationRequestAssembler,
                                 configurationRequestAssembler,
                                 restaurant);
  }

  private ReportService createReportService(InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository,
                                            Restaurant restaurant, IngredientPriceRepository ingredientPriceRepository, IngredientReportGenerator ingredientReportGenerator,
                                            MaterialReportGenerator materialReportGenerator)
  {

    ReportPeriodFactory reportPeriodFactory = new ReportPeriodFactory();

    return new ReportService(inMemoryIngredientQuantityRepository,
                             ingredientPriceRepository,
                             ingredientReportGenerator,
                             restaurant,
                             materialReportGenerator,
                             reportPeriodFactory);
  }

  private Object[] createResources(RestaurantService restaurantService,
                                   ReportService reportService,
                                   ChefReportService chefReportService,
                                   FinanceService financeService)
  {
    ReservationResource reservationResource = createReservationResource(restaurantService);
    ReportResource reportResource = createReportResource(reportService, chefReportService, financeService);
    ConfigurationResource configurationResource = new ConfigurationResource(restaurantService,
                                                                            new ConfigurationDateFormatValidator(DATE_REGEX));

    Collection<Object> resources = new ArrayList<>();
    resources.add(reservationResource);
    resources.add(reportResource);
    resources.add(configurationResource);
    return resources.toArray();
  }

  private ReservationResource createReservationResource(RestaurantService restaurantService) {
    DateFormatValidator dateFormatValidator = new DateFormatValidator(DATE_TIME_REGEX);
    return new ReservationResource(restaurantService, dateFormatValidator);
  }

  private ReportResource createReportResource(ReportService reportService,
                                              ChefReportService chefReportService, FinanceService restaurantFinanceService)
  {
    ReportDateValidator reportDateValidator = new ReportDateValidator(DATE_REGEX, reportService);
    UnitReportDayDtoFactory unitReportDayDtoFactory = new UnitReportDayDtoFactory();
    UnitReportDtoFactory unitReportDtoFactory = new UnitReportDtoFactory(unitReportDayDtoFactory);
    TotalReportDtoFactory totalReportDtoFactory = new TotalReportDtoFactory();
    MaterialReportDtoFactory materialReportDtoFactory = new MaterialReportDtoFactory();
    IngredientReportPresenterFactory ingredientReportPresenterFactory = new IngredientReportPresenterFactory(unitReportDtoFactory,
                                                                                                             totalReportDtoFactory);
    ChefReportDtoAssembler chefReportDtoAssembler = new ChefReportDtoAssembler();
    MaterialReportPresenter materialReportPresenter = new MaterialReportPresenter(materialReportDtoFactory);
    RestaurantFinanceDtoAssembler restaurantFinanceDtoAssembler = new RestaurantFinanceDtoAssembler();

    return new ReportResource(reportService,
                              chefReportService,
                              reportDateValidator,
                              ingredientReportPresenterFactory,
                              chefReportDtoAssembler,
                              materialReportPresenter,
                               restaurantFinanceService,
                                restaurantFinanceDtoAssembler);
  }

  private ReservationServer createServer(Object[] resources) {
    return new ReservationServer(PORT, resources);
  }

  private ReservationIngredientCalculator createReservationIngredientCalculator() {
    FullCourseFactory fullCourseFactory = new FullCourseFactory(new CourseRecipeFactory());
    MenuRepository menuRepository = new InMemoryMenuRepository(fullCourseFactory);
    return new ReservationIngredientCalculator(menuRepository);
  }

  private Set<Available> createAvailableIngredient() {
    Set<Available> availableIngredient = new HashSet<>();
    availableIngredient.add(new TomatoStock(TOMATO, DAY_BEFORE_TOMATO_BECOME_AVAILABLE));
    return availableIngredient;
  }

  private Restaurant createRestaurant(InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository,
                                      ReservationRepository reservationRepository,
                                      AllergiesDetector allergiesDetector,
                                      Buffet buffet,
                                      ChefSelector chefSelector, Set<Available> availableIngredient, IngredientCalculator reservationIngredientCalculator)
  {
    ReservationFactory reservationFactory = createReservationFactory();
    ReservationBook reservationBook = new ReservationBook(reservationRepository);
    Inventory ingredientInventory = new IngredientInventory(inMemoryIngredientQuantityRepository,
                                                                      allergiesDetector, reservationIngredientCalculator, availableIngredient);
    HoppeningEvent hoppeningEvent = createInitialHoppeningEvent();
    return new Restaurant(reservationFactory,
                          reservationBook,
                          ingredientInventory,
                          hoppeningEvent,
                          buffet, chefSelector, reservationRepository);
  }

  private ReservationFactory createReservationFactory() {
    CustomerFactory customerFactory = new CustomerFactory();
    TableFactory tableFactory = new TableFactory(customerFactory);
    return new ReservationFactory(new DateFactory(), tableFactory);
  }

  private HoppeningEvent createInitialHoppeningEvent() {
    Period dinnerDinerPeriod = new DinerPeriod(OPENING_DINNER_LOCAL_DATE, CLOSING_DINNER_LOCAL_DATE);
    Period reservationDinerPeriod = new DinerPeriod(OPENING_RESERVATION_LOCAL_DATE,
                                          CLOSING_RESERVATION_LOCAL_DATE);
    return new HoppeningEvent(dinnerDinerPeriod, reservationDinerPeriod);
  }
}
