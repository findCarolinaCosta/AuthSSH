package com.AuthSSH.ssh.config;

import com.AuthSSH.ssh.utils.Authentication;
import com.AuthSSH.ssh.utils.Authorization;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class HandlerAuthenticationAndAuthorization {
  private HttpSecurity http;
  public HandlerAuthenticationAndAuthorization(HttpSecurity http) throws Exception {
    this.http = http;

    handler();
  }

  public static HttpSecurity execute(HttpSecurity http) throws Exception {
    return new HandlerAuthenticationAndAuthorization(http).http;
  }

  public HttpSecurity handler() throws Exception {

    Authorization.execute(http);

    Authentication.execute(http);

    return http;
  }
}
