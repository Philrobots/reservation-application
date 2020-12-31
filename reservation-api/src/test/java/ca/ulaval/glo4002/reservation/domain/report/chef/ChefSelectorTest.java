package ca.ulaval.glo4002.reservation.domain.report.chef;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.*;

import ca.ulaval.glo4002.reservation.domain.chef.NoChefsAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.builder.CustomerBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.ReservationBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.TableBuilder;
import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefSelector;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;

@ExtendWith(MockitoExtension.class)
class ChefSelectorTest {
  @Mock
  private ChefRepository chefRepository;

  private ChefSelector chefSelector;

  @BeforeEach
  public void setUpChefManagerAndChefRepository() {
    givenSomeChefs();
    chefSelector = new ChefSelector(chefRepository);
  }

  @Test
  public void givenAReservationWithThreeVeganCustomers_whenHireChefsForReservation_thenTheChefWithTheHighestPriorityAndVeganSpecialtyIsHired() {
    // given
    Reservation aVeganReservation = new ReservationBuilder().withTable(givenATable(givenAVeganCustomer(), 3)).build();
    List<Reservation> reservations = Collections.singletonList(aVeganReservation);
    Chef bobSmarties = new Chef("Bob Smarties", ChefPriority.SECOND, Set.of(RestrictionType.VEGAN));
    bobSmarties.addCustomers(3);

    // when
    chefSelector.hireChefsForReservations(givenRestrictionType(reservations), givenADinnerDate(reservations));

    // then
    verify(chefRepository).updateRestaurantChefs(aVeganReservation.getDinnerDate().toLocalDate(),
                                                 Set.of(bobSmarties));
  }

  @Test
  public void givenAReservationWithThreeVegetarianCustomers_whenHireChefsForReservation_thenChefWithHighestPriorityAndVegetarianSpecialtyIsHired() {
    // given
    Reservation aVegetarianReservation = new ReservationBuilder().withTable(givenATable(givenAVegetarianCustomer(), 3)).build();
    List<Reservation> reservations = Collections.singletonList(aVegetarianReservation);
    Chef bobRossbeef = new Chef("Bob Rossbeef", ChefPriority.THIRD, Set.of(RestrictionType.VEGETARIAN));
    bobRossbeef.addCustomers(3);

    // when
    chefSelector.hireChefsForReservations(givenRestrictionType(reservations), givenADinnerDate(reservations));

    // then
    verify(chefRepository).updateRestaurantChefs(aVegetarianReservation.getDinnerDate()
                                                                       .toLocalDate(),
                                                 Set.of(bobRossbeef));
  }

  @Test
  public void givenAReservationWithOneVeganAndOneAllergicCustomer_whenHireChefsForReservation_thenTheChefWithBothSpecialtiesAndTheHighestPriorityIsHired() {
    // given
    Customer anAllergicCustomer = new CustomerBuilder().withRestriction(RestrictionType.ALLERGIES).build();
    Table aVeganTable = new TableBuilder().withCustomer(givenAVeganCustomer()).withCustomer(anAllergicCustomer).build();
    Reservation aVeganReservation = new ReservationBuilder().withTable(aVeganTable).build();
    List<Reservation> reservations = Collections.singletonList(aVeganReservation);
    Chef echarlotteCardin = new Chef("Écharlotte Cardin", ChefPriority.SIXTH, Set.of(RestrictionType.VEGAN, RestrictionType.ALLERGIES));
    echarlotteCardin.addCustomers(2);

    // when
    chefSelector.hireChefsForReservations(givenRestrictionType(reservations), givenADinnerDate(reservations));

    // then
    verify(chefRepository).updateRestaurantChefs(aVeganReservation.getDinnerDate().toLocalDate(),
                                                 Set.of(echarlotteCardin));
  }

  @Test
  public void givenAReservationWithOneNoneOneVegetarianAndOneIllCustomer_whenHireChefsForReservation_thenTheTwoChefsComplementingBothSpecialtiesAndWithTheHighestPrioritiesAreHired() {
    // given
    Customer aNoneCustomer = new CustomerBuilder().withRestriction(RestrictionType.NONE).build();
    Table aVeganTable = new TableBuilder().withCustomer(givenAVegetarianCustomer()).withCustomer(aNoneCustomer).withCustomer(givenAnIllnessCustomer()).build();
    Reservation aVeganReservation = new ReservationBuilder().withTable(aVeganTable).build();
    List<Reservation> reservations = Collections.singletonList(aVeganReservation);
    Chef thierryAki = new Chef("Thierry Aki", ChefPriority.FIRST, Set.of(RestrictionType.NONE));
    Chef ericArdo = new Chef("Éric Ardo", ChefPriority.SEVENTH, Set.of(RestrictionType.VEGETARIAN, RestrictionType.ILLNESS));
    thierryAki.addCustomers(1);
    ericArdo.addCustomers(2);

    // when
    chefSelector.hireChefsForReservations(givenRestrictionType(reservations), givenADinnerDate(reservations));

    // then
    verify(chefRepository).updateRestaurantChefs(aVeganReservation.getDinnerDate().toLocalDate(),
                                                 Set.of(thierryAki, ericArdo));
  }

  @Test
  public void givenNoVegetarianChefsAvailable_whenHireChefsForReservation_thenReservationIsForbidden() {
    // given
    Reservation aVegetarianReservation = new ReservationBuilder().withTable(givenATable(givenAVegetarianCustomer(), 4)).build();
    List<Reservation> reservations = List.of(aVegetarianReservation, aVegetarianReservation, aVegetarianReservation);

    // when
    Executable hiringChefs = () -> chefSelector.hireChefsForReservations(givenRestrictionType(reservations), givenADinnerDate(reservations));

    // then
    assertThrows(NoChefsAvailableException.class, hiringChefs);
  }

  @Test
  public void givenPreviousHiringOfChefs_whenHireChefsForReservation_thenPreviousHiringIsIgnored() {
    // given
    Reservation aVegetarianReservation = new ReservationBuilder().withTable(givenATable(givenAVegetarianCustomer(), 5)).build();
    List<Reservation> previousReservations = List.of(aVegetarianReservation, aVegetarianReservation);
    chefSelector.hireChefsForReservations(givenRestrictionType(previousReservations), givenADinnerDate(previousReservations));
    List<Reservation> newReservations = List.of(aVegetarianReservation);

    // when
    Executable hiringChefs = () -> chefSelector.hireChefsForReservations(givenRestrictionType(newReservations), givenADinnerDate(newReservations));

    // then
    assertDoesNotThrow(hiringChefs);
  }

  @Test
  public void givenFullyBookedDishTypeAndOneExtraOtherCustomer_whenHireChefsForReservations_thenChefsAreHired() {
    // given
    Reservation aBookedNoneReservation = givenABookedNoneReservation();
    Reservation anExtraReservation = new ReservationBuilder().withTable(givenATable(givenAnIllnessCustomer(), 3)).build();
    List<Reservation> reservations = List.of(aBookedNoneReservation, anExtraReservation);

    // when
    Executable hiringChefs = () -> {
      chefSelector.hireChefsForReservations(givenRestrictionType(reservations), givenADinnerDate(reservations));
    };

    // then
    assertDoesNotThrow(hiringChefs);
  }

  @Test
  public void givenANoRestrictionAndIllnessChefsAvailable_whenHireChefsForReservationsWithExtraIllnessCustomer_thenReservationIsForbidden() {
    // given
    Reservation aBookedNoneReservation = givenABookedNoneReservation();
    Reservation aBookedIllnessReservation = givenABookedIllnessReservation();
    Reservation anExtraIllnessReservation = new ReservationBuilder().withTable(givenATable(givenAnIllnessCustomer(), 3)).build();
    List<Reservation> reservations = List.of(aBookedNoneReservation, aBookedIllnessReservation, anExtraIllnessReservation);

    // when
    Executable hiringChefs = () -> {
      chefSelector.hireChefsForReservations(givenRestrictionType(reservations), givenADinnerDate(reservations));
    };

    // then
    assertThrows(NoChefsAvailableException.class, hiringChefs);
  }

  private Reservation givenABookedNoneReservation() {
    Customer aCustomer = new CustomerBuilder().withRestriction(RestrictionType.NONE).build();
    Table aTable = givenRandomTable(aCustomer);
    return new ReservationBuilder().withTable(aTable).withTable(aTable).build();
  }

  private Reservation givenABookedIllnessReservation() {
    Customer aCustomer = new CustomerBuilder().withRestriction(RestrictionType.ILLNESS).build();
    Table aTable = givenRandomTable(aCustomer);
    return new ReservationBuilder().withTable(aTable).withTable(aTable).build();
  }

  private Table givenRandomTable(Customer aCustomer) {
    return new TableBuilder().withCustomer(aCustomer).withCustomer(aCustomer).withCustomer(aCustomer).withCustomer(aCustomer)
            .withCustomer(aCustomer).build();
  }

  private Customer givenAVegetarianCustomer() {
    return new CustomerBuilder().withRestriction(RestrictionType.VEGETARIAN).build();
  }

  private Customer givenAVeganCustomer() {
    return new CustomerBuilder().withRestriction(RestrictionType.VEGAN).build();
  }

  private Customer givenAnIllnessCustomer() {
    return new CustomerBuilder().withRestriction(RestrictionType.ILLNESS).build();
  }


  private Table givenATable(Customer aCustomer, int numberOfCustomer) {
    if (numberOfCustomer ==  3) {
      return new TableBuilder().withCustomer(aCustomer)
              .withCustomer(aCustomer)
              .withCustomer(aCustomer)
              .build();
    } else if (numberOfCustomer == 4) {
      return new TableBuilder().withCustomer(aCustomer).withCustomer(aCustomer)
              .withCustomer(aCustomer)
              .withCustomer(aCustomer)
              .build();
    } else {
      return new TableBuilder().withCustomer(aCustomer).withCustomer(aCustomer)
              .withCustomer(aCustomer).withCustomer(aCustomer)
              .withCustomer(aCustomer)
              .build();
    }
  }
  private Set<Chef> getAvailableChefs() {
    List<Chef> chefs = Arrays.asList(new Chef("Thierry Aki",
                                              ChefPriority.FIRST,
                                              Set.of(RestrictionType.NONE)),
                                     new Chef("Bob Smarties",
                                              ChefPriority.SECOND,
                                              Set.of(RestrictionType.VEGAN)),
                                     new Chef("Bob Rossbeef",
                                              ChefPriority.THIRD,
                                              Set.of(RestrictionType.VEGETARIAN)),
                                     new Chef("Bill Adicion",
                                              ChefPriority.FOURTH,
                                              Set.of(RestrictionType.ALLERGIES)),
                                     new Chef("Omar Calmar",
                                              ChefPriority.FIFTH,
                                              Set.of(RestrictionType.ILLNESS)),
                                     new Chef("Écharlotte Cardin",
                                              ChefPriority.SIXTH,
                                              Set.of(RestrictionType.VEGAN,
                                                     RestrictionType.ALLERGIES)),
                                     new Chef("Éric Ardo",
                                              ChefPriority.SEVENTH,
                                              Set.of(RestrictionType.VEGETARIAN,
                                                     RestrictionType.ILLNESS)),
                                     new Chef("Hans Riz",
                                              ChefPriority.EIGHTH,
                                              Set.of(RestrictionType.NONE,
                                                     RestrictionType.ILLNESS)),
                                     new Chef("Amélie Mélo",
                                              ChefPriority.NINTH,
                                              Set.of(RestrictionType.ALLERGIES,
                                                     RestrictionType.VEGAN)));
    return new HashSet<>(chefs);
  }

  private void givenSomeChefs() {
    given(chefRepository.getAllChefs()).willReturn(getAvailableChefs());
  }

  private List<Map<RestrictionType, Integer>> givenRestrictionType(List<Reservation> reservations) {
    List<Map<RestrictionType, Integer>> restrictionsType = new ArrayList<>();
    for (Reservation reservation : reservations) {
      restrictionsType.add(reservation.getRestrictionTypeCount());
    }
    return restrictionsType;
  }

  private LocalDate givenADinnerDate(List<Reservation> reservations) {
    return reservations.get(0).getDinnerDate().toLocalDate();
  }
}
