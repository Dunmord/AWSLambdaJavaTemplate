package org.falcocloud.lambdatemplate.routing.routes;

import lombok.experimental.SuperBuilder;
import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;
import org.falcocloud.lambdatemplate.model.service.ServiceResponse;

@SuperBuilder
public abstract class Route {
  public abstract ServiceResponse process(final APIGatewayProxyRequest request);
}
