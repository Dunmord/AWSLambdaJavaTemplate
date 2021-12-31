package org.falcocloud.lambdatemplate.routing.routes;

import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;
import org.falcocloud.lambdatemplate.model.service.ServiceResponse;

import java.util.Map;

@SuperBuilder
public class DefaultRoute extends Route {
  public static final String RESOURCE = "/invalidpath/default";
  public static final String HTTP_METHOD = "DEFAULT";
  public static final ImmutablePair<String, String> RESOURCE_HTTP_METHOD_PAIR =
      ImmutablePair.of(RESOURCE, HTTP_METHOD);

  @Override
  public ServiceResponse process(APIGatewayProxyRequest request) {
    return ServiceResponse.builder()
        .statusCode(404)
        .headers(request.getHeaders())
        .body(Map.of(ServiceResponse.HTTP_RESPONSE_KEY, "Not found"))
        .build();
  }
}
