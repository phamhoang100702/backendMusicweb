package project.musicwebsite.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import project.musicwebsite.security.jwt.JwtDecoder;
import project.musicwebsite.security.jwt.JwtToUserPrincipalConverter;
import project.musicwebsite.security.jwt.UserPrincipalAuthenticationToken;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final JwtToUserPrincipalConverter converter;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(converter::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authenticate->{
                    SecurityContextHolder.getContext().setAuthentication(authenticate);
                });
        filterChain.doFilter(request,response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        var token = request.getHeader("Authorization");
        System.out.println("url " + request.getRequestURI());
//        System.out.println("  " + request.());
        System.out.println("token   " + token);
        System.out.println("authorizattion : " );
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
