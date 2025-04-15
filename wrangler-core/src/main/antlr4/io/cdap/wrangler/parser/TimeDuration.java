package io.cdap.wrangler.api.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDuration implements Token {
  private static final Pattern PATTERN = Pattern.compile("(?i)(\\d+(\\.\\d+)?)(ns|us|ms|s|m|h)");
  private final long millis;

  public TimeDuration(String value) {
    Matcher matcher = PATTERN.matcher(value.trim());
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid time duration: " + value);
    }

    double number = Double.parseDouble(matcher.group(1));
    String unit = matcher.group(3).toLowerCase();

    switch (unit) {
      case "ns": millis = (long) (number / 1_000_000); break;
      case "us": millis = (long) (number / 1_000); break;
      case "ms": millis = (long) number; break;
      case "s":  millis = (long) (number * 1000); break;
      case "m":  millis = (long) (number * 60 * 1000); break;
      case "h":  millis = (long) (number * 60 * 60 * 1000); break;
      default: throw new IllegalArgumentException("Unknown time unit: " + unit);
    }
  }

  public long getMillis() {
    return millis;
  }

  @Override
  public String toString() {
    return millis + " ms";
  }
}
