package org.falcocloud.lambdatemplate.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import org.falcocloud.lambdatemplate.dependencies.DependencyFactory;
import org.falcocloud.lambdatemplate.filters.FilterQueue;
import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;
import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyResponse;
import org.falcocloud.lambdatemplate.routing.RoutingAgent;
import org.falcocloud.lambdatemplate.routing.routes.DefaultRoute;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LambdaHandler
    implements RequestHandler<APIGatewayProxyRequest, APIGatewayProxyResponse> {

  private final Gson gson;

  public LambdaHandler() {
    // Initialize the SDK client outside of the handler method so that it can be reused for
    // subsequent invocations.
    // It is initialized when the class is loaded.
    gson = DependencyFactory.gsonInstance();
    // Consider invoking a simple api here to pre-warm up the application, eg: dynamodb#listTables
  }

  @Override
  public APIGatewayProxyResponse handleRequest(
      final APIGatewayProxyRequest request, Context context) {
    var response =
        RoutingAgent.builder()
            .preFilters(getAllPreFilters())
            .routes(Map.of(DefaultRoute.RESOURCE_HTTP_METHOD_PAIR, DefaultRoute.builder().build()))
            .build()
            .process(request);
    return APIGatewayProxyResponse.builder()
        .statusCode(response.getStatusCode())
        .headers(response.getHeaders())
        .body(gson.toJson(response.getBody()))
        .build();
  }

  private FilterQueue getAllPreFilters() {
    return FilterQueue.builder().filters(new LinkedList<>(List.of())).build();
  }
}
