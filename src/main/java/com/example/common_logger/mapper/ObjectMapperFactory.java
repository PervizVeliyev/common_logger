package com.example.common_logger.mapper;

import com.example.common_logger.aspect.LogIgnoreIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

public enum ObjectMapperFactory {

  OBJECT_MAPPER_FACTORY;

  public ObjectMapper createObjectMapperInstance() {
    var objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

  public ObjectMapper getLogIgnoreObjectMapper() {
    var objectMapper = new ObjectMapper();
    var introspector = new LogIgnoreIntrospector();
    objectMapper.setAnnotationIntrospector(introspector);
    objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
    return objectMapper;
  }

}
