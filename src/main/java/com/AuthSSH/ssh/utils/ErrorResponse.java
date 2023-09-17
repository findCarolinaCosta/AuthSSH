package com.AuthSSH.ssh.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ErrorResponse {

  private String timestamp;
  private int status;
  private String error;
  private String path;
}
