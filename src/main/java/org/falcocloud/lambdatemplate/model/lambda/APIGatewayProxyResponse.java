package org.falcocloud.lambdatemplate.model.lambda;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class APIGatewayProxyResponse {
  private int statusCode;
  private Map<String, String> headers;
  private String body;
}
