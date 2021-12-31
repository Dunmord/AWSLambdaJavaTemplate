package org.falcocloud.lambdatemplate.filters;

import lombok.Builder;
import lombok.NonNull;
import lombok.val;
import org.falcocloud.lambdatemplate.model.lambda.APIGatewayProxyRequest;

import java.util.Queue;

@Builder
public class FilterQueue {
  @NonNull private Queue<Filter> filters;

  public void doFilter(final APIGatewayProxyRequest request) throws FilterException {
    for (val filter : filters) {
      filter.process(request);
    }
  }
}
