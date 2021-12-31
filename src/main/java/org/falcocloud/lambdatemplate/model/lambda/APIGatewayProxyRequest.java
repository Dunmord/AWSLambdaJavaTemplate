package org.falcocloud.lambdatemplate.model.lambda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class APIGatewayProxyRequest {
  private String resource;
  private String path;
  private String httpMethod;
  private Map<String, String> headers;
  private Map<String, String> queryStringParameters;
  private Map<String, String> stageVariables;
  private Map<String, String> pathParameters;
  private LinkedHashMap<String, String> identity;
  private String body;
}
