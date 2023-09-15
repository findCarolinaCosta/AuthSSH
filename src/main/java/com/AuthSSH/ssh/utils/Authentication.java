package com.AuthSSH.ssh.utils;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class Authentication {

  private HttpSecurity http;

  public Authentication(HttpSecurity http) throws Exception {
    this.http = http;

    login();
    logout();
  }

  public static HttpSecurity execute(HttpSecurity http) throws Exception {
    return new Authentication(http).http;
  }

  private void login() throws Exception {
    http
        .formLogin(login ->
            login
                .permitAll()
                .defaultSuccessUrl("/swagger-ui/index.html", true)
        );
  }

  private void logout() throws Exception {
    http
        .logout(
            logout -> logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .deleteCookies("SESSION")
        );
  }
}
