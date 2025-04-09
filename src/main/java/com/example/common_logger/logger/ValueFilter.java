package com.example.common_logger.logger;

import org.springframework.web.util.HtmlUtils;

import java.util.EnumSet;

public enum ValueFilter {

  VALUE_FILTER;

  public Object filterValue(Object input) {
    if (input instanceof Throwable) {
      return input;
    }
    return filterNonThrowableValue(input);
  }

  private Object filterNonThrowableValue(Object input) {
    if (input == null) {
      return null;
    }
    String escapedInput = HtmlUtils.htmlEscape(input.toString());
    return EnumSet.allOf(FilterPattern.class).stream().reduce(escapedInput,
        (filteredString, filterPattern) -> filteredString.replaceAll(filterPattern.getRegexp(),
            filterPattern.getMask()), String::concat).replace("\r", "&cr;").replace("\n", "&lf;");
  }

}
