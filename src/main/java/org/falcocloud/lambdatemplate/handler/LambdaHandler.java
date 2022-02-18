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
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LambdaHandler
    implements RequestHandler<APIGatewayProxyRequest, APIGatewayProxyResponse> {

  private final Gson gson;
  private final RoutingAgent routingAgent;

  public LambdaHandler() {
    // Initialize the SDK client outside of the handler method so that it can be reused for
    // subsequent invocations.
    // It is initialized when the class is loaded.
    gson = DependencyFactory.gsonInstance();
    routingAgent =
        RoutingAgent.builder()
            .preFilters(getAllPreFilters())
            .postFilters(getAllPostFilters())
            .routes(Map.of(DefaultRoute.RESOURCE_HTTP_METHOD_PAIR, DefaultRoute.builder().build()))
            .build();
    // Consider invoking a simple api here to pre-warm up the application, eg: dynamodb#listTables
  }

  // For easy unit testing
  public LambdaHandler(final DynamoDbClient dynamoDbClient) {
    gson = DependencyFactory.gsonInstance();
    routingAgent =
        RoutingAgent.builder()
            .preFilters(getAllPreFilters())
            .postFilters(getAllPostFilters())
            .routes(Map.of(DefaultRoute.RESOURCE_HTTP_METHOD_PAIR, DefaultRoute.builder().build()))
            .build();
  }

  @Override
  public APIGatewayProxyResponse handleRequest(
      final APIGatewayProxyRequest request, Context context) {
    var response = routingAgent.process(request);
    return APIGatewayProxyResponse.builder()
        .statusCode(response.getStatusCode())
        .headers(response.getHeaders())
        .body(gson.toJson(response.getBody()))
        .build();
  }

  private FilterQueue getAllPreFilters() {
    return FilterQueue.builder().filters(new LinkedList<>(List.of())).build();
  }

  private FilterQueue getAllPostFilters() {
    return FilterQueue.builder().filters(new LinkedList<>(List.of())).build();
  }
}
