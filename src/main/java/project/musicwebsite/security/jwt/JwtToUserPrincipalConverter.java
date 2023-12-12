package project.musicwebsite.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import project.musicwebsite.security.UserPrincipal;

import java.util.List;

@Component
public class JwtToUserPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt){
        var authorityList = getClaimOrEmptyList(jwt,"au")
                .stream().map(SimpleGrantedAuthority::new)
                .toList();
        return UserPrincipal.builder()
                .id(Long.parseLong(jwt.getSubject()))
                .username(jwt.getClaim("e").asString())
                .authorities(authorityList)
                .build();
    }


    private List<String> getClaimOrEmptyList(DecodedJWT jwt, String claim){
        if(jwt.getClaim(claim).isNull()) return List.of();
        return jwt.getClaim(claim).asList(String.class);
    }

}
