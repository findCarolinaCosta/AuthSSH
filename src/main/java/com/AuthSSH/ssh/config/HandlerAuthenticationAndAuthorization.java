package com.AuthSSH.ssh.config;

import com.AuthSSH.ssh.utils.Authentication;
import com.AuthSSH.ssh.utils.Authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HandlerAuthenticationAndAuthorization {

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {

    Authorization.execute(http);

    Authentication.execute(http);

    return http.build();
  }
}
