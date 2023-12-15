package project.musicwebsite.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.musicwebsite.exception.UnauthorizedHandler;
import project.musicwebsite.service.security.UserDetailServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;
    private final UserDetailServiceImp userDetailServiceImp;
    private final UnauthorizedHandler unauthorizedHandler;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationFilter authenticationFilter, UserDetailServiceImp userDetailServiceImp, UnauthorizedHandler unauthorizedHandler) {
        this.authenticationFilter = authenticationFilter;
        this.userDetailServiceImp = userDetailServiceImp;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public SecurityFilterChain applicationSecutiry(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(h->h.authenticationEntryPoint(unauthorizedHandler))
                .securityMatcher("/**")
                .authorizeHttpRequests(registry->registry
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/singer/**").hasAnyAuthority("SINGER","ADMIN","CENSOR")
                        .requestMatchers("/api/v1/user/**").hasAnyAuthority("USER","CENSOR","ADMIN")
                        .requestMatchers("/api/v1/singer/{id1}/user/{id2}").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{
        var builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(userDetailServiceImp)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

}
