package com.AuthSSH.ssh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SessionManagement {
  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain sessionManagementFilterChain(HttpSecurity http) throws Exception {

    rememberMe(http);

    sessionManagement(http);

    return http.build();
  }

  private HttpSecurity rememberMe(HttpSecurity http) throws Exception {
    return http
        .rememberMe(rememberMe ->
            rememberMe
                .tokenValiditySeconds(86400)
                .key("rememberTracker")
        );
  }

  private HttpSecurity sessionManagement(HttpSecurity http) throws Exception {
    return http
        .sessionManagement(sessionManagement ->
            sessionManagement
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login?expired")
        );

  }
}
