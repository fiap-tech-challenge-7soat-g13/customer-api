package com.fiap.challenge.customer.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
@Profile("!test")
public class AwsConfig {

    @Bean
    public CognitoIdentityProviderClient cognitoIdentityProviderClientClient() {
//        return CognitoIdentityProviderClient.builder()
//                .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
//                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
//                .build();
        return null;
    }

}
