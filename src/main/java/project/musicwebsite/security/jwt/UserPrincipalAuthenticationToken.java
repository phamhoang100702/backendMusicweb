package project.musicwebsite.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import project.musicwebsite.security.UserPrincipal;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;
    public UserPrincipalAuthenticationToken(UserPrincipal principal){
        super(principal.getAuthorities());
        this.userPrincipal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }
}
