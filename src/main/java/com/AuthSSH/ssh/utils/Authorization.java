package com.AuthSSH.ssh.utils;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class Authorization {
  private HttpSecurity http;

  public Authorization(HttpSecurity http) throws Exception {
    this.http = http;

    authorization();
  }

  public static HttpSecurity execute(HttpSecurity http) throws Exception {
    return new Authorization(http).http;
  }
  private void authorization() throws Exception {
    http
        .authorizeHttpRequests(authorize ->
            authorize
                .requestMatchers("/login", "/error/**").permitAll()
                .anyRequest().authenticated()
        );
  }
}
