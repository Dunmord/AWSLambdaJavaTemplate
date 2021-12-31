package org.falcocloud.lambdatemplate.filters;

public class FilterException extends Exception {
  public FilterException(final Exception e) {
    super(e.getMessage(), e);
  }
}
