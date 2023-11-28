package project.musicwebsite.s3;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.bucket")
@Getter
@Setter
public class S3Bucket {
    private String name;


}
