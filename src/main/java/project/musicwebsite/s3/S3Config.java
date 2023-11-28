package project.musicwebsite.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${aws.region}")
    private String region;
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;

    @Bean
    public S3Client S3Clent(){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey,secretKey);
        Region region1 = Region.of(region);
        return S3Client.builder()
                .region(region1)
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }
}
