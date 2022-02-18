package org.falcocloud.lambdatemplate.routing;

import lombok.Builder;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.falcocloud.lambdatemplate.filters.FilterException;
import org.falcocloud.lambdatemplate.filters.FilterQueue;
import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;
import org.falcocloud.lambdatemplate.model.service.ServiceResponse;
import org.falcocloud.lambdatemplate.routing.routes.DefaultRoute;
import org.falcocloud.lambdatemplate.routing.routes.Route;

import java.util.Map;

@Builder
public class RoutingAgent {

  private final Map<Pair<String, String>, Route> routes;
  @NonNull private FilterQueue preFilters;
  private FilterQueue postFilters;

  public ServiceResponse process(final APIGatewayProxyRequest request) {
    try {
      preFilters.doFilter(request);
    } catch (final FilterException e) {
      return ServiceResponse.builder()
          .statusCode(500)
          .headers(Map.of())
          .body(
              Map.of(
                  ServiceResponse.HTTP_RESPONSE_KEY,
                  "An error has occurred. Please contact the system administrator."))
          .build();
    }
    val response =
        routes
            .getOrDefault(
                ImmutablePair.of(request.getResource(), request.getHttpMethod()),
                routes.get(DefaultRoute.RESOURCE_HTTP_METHOD_PAIR))
            .process(request);
    try {
      postFilters.doFilter(request);
    } catch (final FilterException e) {
      return ServiceResponse.builder()
          .statusCode(500)
          .headers(Map.of())
          .body(
              Map.of(
                  ServiceResponse.HTTP_RESPONSE_KEY,
                  "An error has occurred. Please contact the system administrator."))
          .build();
    }
    return response;
  }
}
