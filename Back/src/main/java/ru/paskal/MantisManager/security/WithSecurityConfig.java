package ru.paskal.MantisManager.security;

import static ru.paskal.MantisManager.utils.TestLogger.log;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.paskal.MantisManager.security.handlers.UnauthorizedHandler;
import ru.paskal.MantisManager.security.jwt.JwtAuthFilter;
import ru.paskal.MantisManager.security.user.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ComponentScan("ru.paskal.MantisManager")
@Profile("with-security")
public class WithSecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  private final UserDetailsServiceImpl userDetailService;

  private final UnauthorizedHandler unauthorizedHandler;


  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailService);
    return authenticationManagerBuilder.build();
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    log("sec conf");
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(AbstractHttpConfigurer::disable)
        .exceptionHandling(conf -> conf
            .authenticationEntryPoint(unauthorizedHandler)
        )
        .securityMatcher("/**")
        .authorizeHttpRequests(registry -> registry
            .requestMatchers("/").permitAll()
            .requestMatchers("/auth/login").permitAll()
            .requestMatchers("/auth/register").permitAll()

            .anyRequest().authenticated()
        );
    return http.build();
  }

}
