package com.example.common_logger.aspect;

import com.example.common_logger.annotation.LogIgnore;
import com.example.common_logger.logger.CommonLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

import static com.example.common_logger.mapper.ObjectMapperFactory.OBJECT_MAPPER_FACTORY;

@Aspect
@Component
public class LoggingAspect {

  @Around("within(@com.example.common_logger.annotation.Log *) || @annotation(com.example.common_logger.annotation.Log))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    CommonLogger log = CommonLogger.getLogger(joinPoint.getTarget().getClass());
    StringBuilder parameters = buildParameters(signature, joinPoint.getArgs());

    logInfoEvent(log, signature, parameters);

    try {
      Object response = joinPoint.proceed();
      logEndAction(log, signature, response);
      return response;
    } catch (Throwable throwable) {
      logExceptionEvent(log, signature, parameters, throwable);
      throw throwable;
    }
  }

  private StringBuilder buildParameters(MethodSignature signature, Object[] args) {
    StringBuilder builder = new StringBuilder();
    Parameter[] parameters = signature.getMethod().getParameters();
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i].isAnnotationPresent(LogIgnore.class)) {
        continue;
      }
      builder.append(" ").append(parameters[i].getName())
          .append(":").append(writeObjectAsString(args[i], parameters[i]));
    }
    return builder;
  }

  private String writeObjectAsString(Object object, Parameter parameter) {
    try {
      return parameter.getType().getTypeName() + " "
          + OBJECT_MAPPER_FACTORY.getLogIgnoreObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      return object.toString();
    }
  }

  private void logInfoEvent(CommonLogger log, MethodSignature signature,
                            StringBuilder parameters) {
    log.info("ActionLog.{}.{}.{}", signature.getName(), "start", parameters);
  }

  private void logExceptionEvent(CommonLogger log, MethodSignature signature,
                                 StringBuilder parameters,
                                 Throwable throwable) {
    log.error("ActionLog.{}.{}.{} - Exception: {}", signature.getName(), "error", parameters,
        throwable.getMessage());
  }

  private void logEndAction(CommonLogger log, MethodSignature signature, Object response) {
    if (void.class.equals(signature.getReturnType())) {
      log.info("ActionLog.{}.end", signature.getName());
    } else {
      log.info("ActionLog.{}.end - Response: {}", signature.getName(), response);
    }
  }

}
