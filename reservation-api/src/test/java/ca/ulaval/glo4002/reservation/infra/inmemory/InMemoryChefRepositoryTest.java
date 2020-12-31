package ca.ulaval.glo4002.reservation.infra.inmemory;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

@ExtendWith(MockitoExtension.class)
public class InMemoryChefRepositoryTest {
  private static final ChefPriority A_CHEF_TYPE = ChefPriority.NINTH;
  private static final String A_CHEF_NAME = "A Name";
  private static final Set<RestrictionType> SOME_SPECIALTIES = Set.of(RestrictionType.NONE);
  private static final Chef A_CHEF = new Chef(A_CHEF_NAME, A_CHEF_TYPE, SOME_SPECIALTIES);

  private InMemoryChefRepository inMemoryChefRepository;

  @BeforeEach
  public void setUpChefRepository() {
    inMemoryChefRepository = new InMemoryChefRepository();
  }

  @Test
  public void givenNoReservation_whenRestaurantChefByDayIsEmpty_thenMapShouldBeEmpty() {
    // when
    Map<LocalDate, Set<Chef>> chefsByDay = inMemoryChefRepository.getAllChefsWorkSchedule();

    // then
    assertThat(chefsByDay).isEmpty();
  }

  @Test
  public void whenUpdateRestaurantChef_thenChefsAreUpdated() {
    // given
    LocalDate dinnerDate = LocalDate.of(2000, 12, 12);
    Set<Chef> newChefs = Set.of(A_CHEF);

    // when
    inMemoryChefRepository.updateRestaurantChefs(dinnerDate, newChefs);

    // then
    assertThat(inMemoryChefRepository.getAllChefsWorkSchedule()).containsEntry(dinnerDate,
                                                                               newChefs);
  }

  @Test
  public void givenChefsInPersistence_whenGetAllChefs_thenAllChefsAreReturned() {
    // given
    LocalDate aDate = LocalDate.of(12, 12, 12);
    LocalDate anotherDate = LocalDate.of(13, 12, 12);
    Set<Chef> someChefs = Set.of(A_CHEF);
    Set<Chef> someOtherChefs = Set.of(A_CHEF);
    inMemoryChefRepository.updateRestaurantChefs(aDate, someChefs);
    inMemoryChefRepository.updateRestaurantChefs(anotherDate, someOtherChefs);

    Map<LocalDate, Set<Chef>> expectedChefs = Map.of(aDate, someChefs, anotherDate, someOtherChefs);

    // when
    Map<LocalDate, Set<Chef>> actualChefs = inMemoryChefRepository.getAllChefsWorkSchedule();

    // then
    assertThat(actualChefs).isEqualTo(expectedChefs);
  }
}
