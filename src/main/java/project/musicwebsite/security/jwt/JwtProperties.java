package project.musicwebsite.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.logging.Logger;

@Component
@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token-duration}")
    private Duration tokenDuration;

}
