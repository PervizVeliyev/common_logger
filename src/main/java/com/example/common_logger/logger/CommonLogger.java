package com.example.common_logger.logger;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.Arrays;

import static com.example.common_logger.logger.ValueFilter.VALUE_FILTER;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CommonLogger {

  Logger logger;

  public static CommonLogger getLogger(Class<?> clazz) {
    Logger logger = LoggerFactory.getLogger(clazz);
    return new CommonLogger(logger);
  }

  public Object[] filterValues(Object... argArray) {
    return Arrays.stream(argArray).map(VALUE_FILTER::filterValue).toArray();
  }

  public String getName() {
    return logger.getName();
  }

  public void trace(String s, Object... args) {
    if (logger.isTraceEnabled()) {
      logger.trace(s, filterValues(args));
    }
  }

  public void trace(Marker marker, String s, Object... args) {
    if (logger.isTraceEnabled(marker)) {
      logger.trace(marker, s, filterValues(args));
    }
  }

  public void debug(String s, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(s, filterValues(args));
    }
  }

  public void debug(Marker marker, String s, Object... args) {
    if (logger.isDebugEnabled(marker)) {
      logger.debug(marker, s, filterValues(args));
    }
  }

  public void info(String s, Object... args) {
    if (!logger.isInfoEnabled()) {
      return;
    }
    logger.info(s, args);
  }

  public void info(Marker marker, String s, Object... args) {
    if (logger.isInfoEnabled(marker)) {
      logger.info(marker, s, filterValues(args));
    }
  }

  public void warn(String s, Object... args) {
    if (logger.isWarnEnabled()) {
      logger.warn(s, filterValues(args));
    }
  }

  public void warn(Marker marker, String s, Object... args) {
    if (logger.isWarnEnabled(marker)) {
      logger.warn(marker, s, filterValues(args));
    }
  }

  public void error(String s, Object... args) {
    if (!logger.isErrorEnabled()) {
      return;
    }
    logger.error(s, args);
  }

  public void error(Marker marker, String s, Object... args) {
    if (logger.isErrorEnabled(marker)) {
      logger.error(marker, s, filterValues(args));
    }
  }

}
