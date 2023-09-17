package com.AuthSSH.ssh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {

    HandlerAuthenticationAndAuthorization.execute(http);
    SessionManagement.execute(http);
    SecurityHandler.execute(http);

    http
        .csrf(AbstractHttpConfigurer::disable); // csrf protection disabled

    return http.build();
  }
}
