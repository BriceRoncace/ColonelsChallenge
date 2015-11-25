package gov.idaho.isp.fitness.challenge.utils;

public class StringUtils {
  public static String trimToNull(String str) {
    if (str == null) {
      return null;
    }

    str = str.trim();
    return "".equals(str) ? null : str;
  }
}
