package project.musicwebsite.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Data
@Component
public class JwtIssuer {
    @Autowired
    private final JwtProperties jwtProperties;
    private String issue(Request request){
        var now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(request.userid))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(jwtProperties.getTokenDuration()))
                .withClaim("e",request.getEmail())
                .withClaim("au",request.getRoles())
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    @Data
    @Builder
    public static class Request{
        private final Long userid;
        private final String email;
        private final List<String> roles;
    }



}

