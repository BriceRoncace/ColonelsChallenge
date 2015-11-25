package gov.idaho.isp.fitness.challenge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MathUtils {
  public static BigDecimal getMean(Iterable<BigDecimal> iterableValues) {
    if (iterableValues == null) {
      return BigDecimal.ZERO;
    }

    List<BigDecimal> values = asList(iterableValues);
    Collections.sort(values);

    if (values.isEmpty()) {
      return BigDecimal.ZERO;
    }

    BigDecimal sum = values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    return sum.divide(BigDecimal.valueOf(values.size()), RoundingMode.HALF_UP);
  }

  public static BigDecimal getMedian(Iterable<BigDecimal> iterableValues) {
    if (iterableValues == null) {
      return BigDecimal.ZERO;
    }

    List<BigDecimal> values = asList(iterableValues);
    Collections.sort(values);

    if (values.isEmpty()) {
      return BigDecimal.ZERO;
    }

    if (values.size() % 2 != 0) {
      return values.get(values.size()/2);
    }
    else {
      BigDecimal lowerMiddle = values.get(values.size()/2);
      BigDecimal upperMiddle = values.get(values.size()/2 - 1);
      return lowerMiddle.add(upperMiddle).divide(new BigDecimal("2"), RoundingMode.HALF_UP);
    }
  }

  private static final BigDecimal SECONDS_IN_HOUR = new BigDecimal("3600");

  public static BigDecimal secondsToHours(Long seconds) {
    if (seconds == null) {
      seconds = 0L;
    }
    return BigDecimal.valueOf(seconds).divide(SECONDS_IN_HOUR, 2, RoundingMode.HALF_UP);
  }

  private static List<BigDecimal> asList(Iterable<BigDecimal> iterableValues) {
    List<BigDecimal> values = new ArrayList<>();
    iterableValues.forEach(values::add);
    values.removeAll(Collections.singleton(null));
    return values;
  }
}