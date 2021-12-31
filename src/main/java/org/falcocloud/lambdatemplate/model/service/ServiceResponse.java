package org.falcocloud.lambdatemplate.model.service;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@Data
public class ServiceResponse {
  public static final String ERROR_KEY = "error";
  public static final String HTTP_RESPONSE_KEY = "httpResponseMessage";

  protected int statusCode;
  protected Map<String, String> headers;
  protected Map<String, Object> body;
}
