package com.AuthSSH.ssh.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ServiceResponse {
  private boolean result;
  private HttpStatus status;
  private String message;
}
