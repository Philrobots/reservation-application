package ca.ulaval.glo4002.reservation.domain.chef;

import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Selectable {
    void hireChefsForReservations(List<Map<RestrictionType, Integer>> reservationsRestriction, LocalDate dinnerDate);
}
