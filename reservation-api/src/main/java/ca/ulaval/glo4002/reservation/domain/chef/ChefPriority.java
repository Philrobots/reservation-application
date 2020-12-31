package ca.ulaval.glo4002.reservation.domain.chef;

public enum ChefPriority {
  FIRST(9), SECOND(8), THIRD(7), FOURTH(6), FIFTH(5), SIXTH(4), SEVENTH(3), EIGHTH(2), NINTH(1);

  private final int priorityValue;

  ChefPriority(int priorityValue) {
    this.priorityValue = priorityValue;
  }

  public int getPriorityValue() {
    return priorityValue;
  }
}
