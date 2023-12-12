//package project.musicwebsite.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import project.musicwebsite.exception.UnauthorizedHandler;
//import project.musicwebsite.service.security.UserDetailServiceImp;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//    private final JwtAuthenticationFilter authenticationFilter;
//    private final UserDetailServiceImp userDetailServiceImp;
//    private final UnauthorizedHandler unauthorizedHandler;
//
//    @Bean
//    public SecurityFilterChain applicationSecutiry(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        httpSecurity
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .formLogin(AbstractHttpConfigurer::disable)
//                .exceptionHandling(h->h.authenticationEntryPoint(unauthorizedHandler))
//                .securityMatcher("/**")
//                .authorizeHttpRequests(registry->registry
//                        .requestMatchers("/api/v1/singer").permitAll()
//                        .requestMatchers("/api/v1/user").hasAnyAuthority("ROLE_USER","ROLE_CENSOR")
//                        .requestMatchers("/authen/login").permitAll()
//                        .anyRequest().authenticated()
//                );
//        return httpSecurity.build();
//    }
//
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{
//        var builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        builder
//                .userDetailsService(userDetailServiceImp)
//                .passwordEncoder(passwordEncoder());
//        return builder.build();
//    }
//
//}
