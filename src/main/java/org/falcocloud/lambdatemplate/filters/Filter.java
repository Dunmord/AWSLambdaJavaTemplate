package org.falcocloud.lambdatemplate.filters;

import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;

public interface Filter {
  void process(final APIGatewayProxyRequest request) throws FilterException;
}
