package com.sonatype;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.amazonaws.regions.Regions.US_EAST_2;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.sonatype.repository")
public class DynamoDBConfig
{
  @Value("${amazon.dynamodb.endpoint}")
  private String amazonDynamoDBEndpoint;

  @Value("${amazon.aws.accesskey}")
  private String amazonAWSAccessKey;

  @Value("${amazon.aws.secretkey}")
  private String amazonAWSSecretKey;

  @Value("${amazon.aws.role}")
  private String role;

  private static final String SESSION_NAME = "sample";

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    return AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, US_EAST_2.getName()))
        .withCredentials(credentialsProvider())
        .build();
  }

  @Bean
  @Primary
  public AWSCredentialsProvider credentialsProvider() {

    AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
        .withRegion(US_EAST_2)
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)))
        .build();
    return new STSAssumeRoleSessionCredentialsProvider
        .Builder(role, SESSION_NAME)
        .withStsClient(stsClient)
        .build();
  }
}
