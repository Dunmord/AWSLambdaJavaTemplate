package org.falcocloud.lambdatemplate.handler;

import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;
import org.falcocloud.lambdatemplate.routing.routes.DefaultRoute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class LambdaHandlerTest {

  @Test
  public void handleRequest_shouldReturnDefaultRoute() {
    LambdaHandler handler = new LambdaHandler();
    var response =
        handler.handleRequest(
            APIGatewayProxyRequest.builder()
                .body("test")
                .headers(Map.of("header1", "value1", "header2", "value2"))
                .httpMethod(DefaultRoute.HTTP_METHOD)
                .resource(DefaultRoute.RESOURCE)
                .build(),
            null);
    Assertions.assertEquals(404, response.getStatusCode());
    Assertions.assertEquals("{\"httpResponseMessage\":\"Not found\"}", response.getBody());
    Assertions.assertEquals(response.getHeaders().size(), 2);
  }

  @Test
  public void handleRequest_shouldReturnDefaultRoute_WhenRouteDoesNotExist() {
    LambdaHandler handler = new LambdaHandler();
    var response =
        handler.handleRequest(
            APIGatewayProxyRequest.builder()
                .body("test")
                .headers(Map.of("header1", "value1", "header2", "value2"))
                .httpMethod("GET")
                .resource("SomeRandomRoute")
                .build(),
            null);
    Assertions.assertEquals(404, response.getStatusCode());
    Assertions.assertEquals("{\"httpResponseMessage\":\"Not found\"}", response.getBody());
    Assertions.assertEquals(response.getHeaders().size(), 2);
  }
}
