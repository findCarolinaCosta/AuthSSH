package com.AuthSSH.ssh.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class SessionManagement {
  private HttpSecurity http;


  public SessionManagement(HttpSecurity http) throws Exception {
    this.http = http;

    sessionManagementFilterChain();
  }

  public static HttpSecurity execute(HttpSecurity http) throws Exception {
    return new SessionManagement(http).http;
  }

  private HttpSecurity sessionManagementFilterChain() throws Exception {

    rememberMe();

    sessionManagement();

    return http;
  }

  private HttpSecurity rememberMe() throws Exception {
    return http
        .rememberMe(rememberMe ->
            rememberMe
                .tokenValiditySeconds(86400)
                .key("rememberTracker")
        );
  }

  private HttpSecurity sessionManagement() throws Exception {
    return http
        .sessionManagement(sessionManagement ->
            sessionManagement
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login?expired")
        );

  }
}
