package gov.idaho.isp.fitness.challenge;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Badge {
  GATOR_1(Type.GATOR, "The Gator: Situp/Pushup Leader", "alligator1.png"),
  GATOR_2(Type.GATOR, "The Gator: Situp/Pushup Runner-up", "alligator2.png"),
  GATOR_3(Type.GATOR, "The Gator: Situp/Pushup Second Runner-up", "alligator3.png"),
  GATOR_TOP_TEN(Type.GATOR, "The Gator: Situp/Pushup Top Ten Performer", "alligator10.png"),

  BEAST_1(Type.BEAST, "The Beast: Resistance Leader", "rhino1.png"),
  BEAST_2(Type.BEAST, "The Beast: Resistance Runner-up", "rhino2.png"),
  BEAST_3(Type.BEAST, "The Beast: Resistance Second Runner-up", "rhino3.png"),
  BEAST_TOP_TEN(Type.BEAST, "The Beast: Resistance Top Ten Performer", "rhino10.png"),

  LONG_HAULER_1(Type.LONG_HAULER, "The Long Hauler: Aereobic Time Leader", "camel1.png"),
  LONG_HAULER_2(Type.LONG_HAULER, "The Long Hauler: Aereobic Time Runner-up", "camel2.png"),
  LONG_HAULER_3(Type.LONG_HAULER, "The Long Hauler: Aereobic Time Second Runner-up", "camel3.png"),
  LONG_HAULER_TOP_TEN(Type.LONG_HAULER, "The Long Hauler: Aereobic Time Top Ten Performer", "camel10.png"),

  CHEETAH_1(Type.CHEETAH, "The Cheetah: Run/Walk Mileage Leader", "cheetah1.png"),
  CHEETAH_2(Type.CHEETAH, "The Cheetah: Run/Walk Mileage Runner-up", "cheetah2.png"),
  CHEETAH_3(Type.CHEETAH, "The Cheetah: Run/Walk Mileage Second Runner-up", "cheetah3.png"),
  CHEETAH_TOP_TEN(Type.CHEETAH, "The Cheetah: Run/Walk Mileage Top Ten Performer", "cheetah10.png"),

  CYCLIST_1(Type.CYCLIST, "The Cyclist: Bike Mileage Leader", "bear1.png"),
  CYCLIST_2(Type.CYCLIST, "The Cyclist: Bike Mileage Runner-up", "bear2.png"),
  CYCLIST_3(Type.CYCLIST, "The Cyclist: Bike Mileage Second Runner-up", "bear3.png"),
  CYCLIST_TOP_TEN(Type.CYCLIST, "The Cyclist: Bike Mileage Top Ten Performer", "bear10.png"),

  SHARK_1(Type.SHARK, "The Shark: Swim Mileage Leader", "shark1.png"),
  SHARK_2(Type.SHARK, "The Shark: Swim Mileage Runner-up", "shark2.png"),
  SHARK_3(Type.SHARK, "The Shark: Swim Mileage Second Runner-up", "shark3.png"),
  SHARK_TOP_TEN(Type.SHARK, "The Shark: Swim Mileage Top Ten Performer", "shark10.png"),

  ONE_THIRDER(Type.ONE_THIRDER, "The One Thirder: 1/3 goals complete", "trophy-one-third-complete.png"),
  TWO_THIRDER(Type.TWO_THIRDER, "The Two Thirder: 2/3 goals complete", "trophy-two-thirds-complete.png"),
  VICTOR(Type.VICTOR, "The Victor: All goals complete", "trophy.png"),

  MERMAID(Type.MERMAID, "The Mermaid: First female to reach 1 mile swam", "mermaid.png"),
  HONORARY_MERMAID(Type.MERMAID, "The (Honorary) Mermaid: Honorarily conferred (to Amy and Stevie)", "mermaid.png"),
  SEA_KING(Type.SEA_KING, "The Sea King: First male to reach 10 miles swam", "merman.png"),
  WORKER(Type.WORKER, "The Worker Ant: Single workout at or over 2 hours in duration", "ant.png"),
  BEE(Type.BEE, "The Worker Bee: Workouts on 50 or more days of the 10 week challenge", "bee.png"),
  GAZELLE(Type.GAZELLE, "The Gazelle: Single run of 10 or more miles", "gazelle.png"),
  LUCKY_DUCK(Type.LUCKY_DUCK, "The Lucky Duck: Randomly awarded to one lucky participant per week (must play to win)", "duck.png"),
  EARLY_BIRD(Type.EARLY_BIRD, "The Early Bird: Five or more days with workouts within the first week of the challenge", "bird.png"),
  HIGH_FLIER(Type.HIGH_FLIER, "The Highflier: Completed a goal that was not completed last year", "eagle.png"),
  EXTREME_LIFTER(Type.EXTREME_LIFTER, "The Extreme Lifter: lifting 100,000lbs or more in a single workout (aka If the bar ain't bending, you're just pretending)", "barbell.png");

  /*
  Other secret ideas
  - top performer in all three fitness categories for any single given day
  - first to reach the goal in each area
  - first to reach all three goals

  - completed a single work out of over 25 miles (bike)
  - completed a single work out of over 10 miles (run)
  - completed a single work out of over 1 miles (swim)
  - completed a single work out of over 200 situps/pushups
  - top non-commissioned lifter

  - completed a single workout with less than 10 situps/pushups
  - completed a single run/walk workout with less than 1 mile
  */

  private final Badge.Type type;
  private final String label;
  private final String image;

  private Badge(Badge.Type type, String label, String image) {
    this.type = type;
    this.label = label;
    this.image = image;
  }

  public Badge.Type getType() {
    return type;
  }

  public String getLabel() {
    return label;
  }

  public String getImage() {
    return image;
  }

  public static Badge valueOf(Type type, Integer rank) {
    try {
      if (rank > 0 && rank < 4) {
        return Badge.valueOf(type.name() + "_" + rank);
      }
      else if (rank > 0 && rank < 11) {
        return Badge.valueOf(type.name() + "_TOP_TEN");
      }
    }
    catch(IllegalArgumentException ignore) {
    }

    return null;
  }

  public enum Category {
    PERFORMANCE,
    COMPLETION,
    SECRET
  };

  public enum Type {
    GATOR(Category.PERFORMANCE, "The Gator", "Awarded to those with the highest rank in number of situps / pushups."),
    BEAST(Category.PERFORMANCE, "The Beast", "Awarded to those with the highest rank in resistance (i.e. greatest number of total pounds lifted)."),
    LONG_HAULER(Category.PERFORMANCE, "The Long Hauler", "Awarded to those with the highest rank in total aerobic hours."),
    CHEETAH(Category.PERFORMANCE, "The Cheetah", "Awarded to those with the highest rank in miles ran or walked."),
    CYCLIST(Category.PERFORMANCE, "The Cyclist", "Awarded to those with the highest rank in miles biked."),
    SHARK(Category.PERFORMANCE, "The Shark", "Awarded to those with the highest rank in miles swam."),

    ONE_THIRDER(Category.COMPLETION, "The One Thirder", "Awarded to those with 100% completion in one challenge category."),
    TWO_THIRDER(Category.COMPLETION, "The Two Thirder", "Awarded to those with 100% completion in two challenge categories."),
    VICTOR(Category.COMPLETION, "The Victor", "Awarded to those with 100% completion in all three challenge categories. Well done."),

    MERMAID(Category.SECRET, "The Mermaid", "Awarded to first female swimmer to swim 1 mile."),
    SEA_KING(Category.SECRET, "The Sea King", "Awarded to first male swimmer to swim 10 miles."),
    WORKER(Category.SECRET, "The Worker Ant", "Awarded to those with a single workout at or over 2 hours in duration."),
    BEE(Category.SECRET, "The Workder Bee", "Awared to those with workouts on 50 or more days during the 10 week challenge."),
    GAZELLE(Category.SECRET, "The Gazelle", "Awarded to those with a single run of 10 or more miles."),
    LUCKY_DUCK(Category.SECRET, "The Lucky Duck", "The Lucky Duck: Randomly awarded to one lucky participant per week (must play to win)"),
    EARLY_BIRD(Category.SECRET, "The Early Bird", "Awarded to those with 5 or more days with workouts recorded within the first week of the challenge"),
    HIGH_FLIER(Category.SECRET, "The Highflier", "Awarded to those accomplishing a goal that was not accomplished last year"),
    EXTREME_LIFTER(Category.SECRET, "The Extreme Lifter", "Awarded to those lifting 100,000lbs or more in a single workout");

    private final Category category;
    private final String label;
    private final String description;

    private Type(Category category, String label, String description) {
      this.category = category;
      this.label = label;
      this.description = description;
    }

    public Category getCategory() {
      return category;
    }

    public String getLabel() {
      return label;
    }

    public String getDescription() {
      return description;
    }
  };
}