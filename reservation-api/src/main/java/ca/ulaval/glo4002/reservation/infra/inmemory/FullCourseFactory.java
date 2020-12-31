package ca.ulaval.glo4002.reservation.infra.inmemory;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.FullCourse;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class FullCourseFactory {
  private final CourseRecipeFactory courseRecipeFactory;

  public FullCourseFactory(CourseRecipeFactory courseRecipeFactory) {
    this.courseRecipeFactory = courseRecipeFactory;
  }

  public Map<RestrictionType, FullCourse> create() {
    Map<RestrictionType, FullCourse> restrictionTypeToFullCourseMap = new HashMap<>();
    for (RestrictionType restrictionType : RestrictionType.values()) {
      FullCourse fullCourse = new FullCourse(courseRecipeFactory.create(restrictionType));
      restrictionTypeToFullCourseMap.put(restrictionType, fullCourse);
    }
    return restrictionTypeToFullCourseMap;
  }
}
