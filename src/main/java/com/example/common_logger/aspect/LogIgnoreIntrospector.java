package com.example.common_logger.aspect;

import com.example.common_logger.annotation.LogIgnore;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import static java.util.Objects.nonNull;

public class LogIgnoreIntrospector extends JacksonAnnotationIntrospector {

  @Override
  public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
    LogIgnore logIgnore = annotatedMember.getAnnotation(LogIgnore.class);
    if (nonNull(logIgnore)) {
      return true;
    }
    return super.hasIgnoreMarker(annotatedMember);
  }

}
