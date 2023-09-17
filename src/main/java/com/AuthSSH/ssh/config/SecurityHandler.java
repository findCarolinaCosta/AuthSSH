package com.AuthSSH.ssh.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SecurityHandler {
  private HttpSecurity http;

  public SecurityHandler(HttpSecurity http) throws Exception {
    this.http = http;

    handler();
  }

  public static HttpSecurity execute(HttpSecurity http) throws Exception {
    return new SecurityHandler(http).http;
  }

  private HttpSecurity handler() throws Exception {
    return http
        .exceptionHandling(exception ->
            exception
                .defaultAuthenticationEntryPointFor(
                    new CustomAuthenticationEntryPoint(),
                    new AntPathRequestMatcher(HttpMethod.POST.toString(), "/**")
                )
                .accessDeniedHandler(new CustomAccessDeniedHandler())
        );
  }
}
