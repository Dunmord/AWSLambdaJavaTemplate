package org.falcocloud.lambdatemplate.dependencies;

import com.google.gson.Gson;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DependencyFactory {

  private DependencyFactory() {}

  /** @return an instance of DynamoDbClient */
  public static DynamoDbClient dynamoDbClient() {
    return DynamoDbClient.builder()
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .region(Region.US_WEST_2)
        .httpClientBuilder(UrlConnectionHttpClient.builder())
        .build();
  }

  public static Gson gsonInstance() {
    return new Gson();
  }
}
