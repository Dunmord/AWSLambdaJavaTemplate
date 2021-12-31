package org.falcocloud.lambdatemplate.routing;

import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.falcocloud.lambdatemplate.filters.FilterException;
import org.falcocloud.lambdatemplate.filters.FilterQueue;
import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;
import org.falcocloud.lambdatemplate.model.service.ServiceResponse;
import org.falcocloud.lambdatemplate.routing.routes.Route;

import java.util.Map;

@Builder
public class RoutingAgent {
  private static final ImmutablePair<String, String> DEFAULT_ENVIRONMENT_KEY_PAIR =
      ImmutablePair.of("/invalidpath/default", "DEFAULT");
  private final Map<Pair<String, String>, Route> routes;
  @NonNull private FilterQueue preFilters;

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
    return routes
        .getOrDefault(
            ImmutablePair.of(request.getResource(), request.getHttpMethod()),
            routes.get(DEFAULT_ENVIRONMENT_KEY_PAIR))
        .process(request);
  }
}
