package com.AuthSSH.ssh.dtos;

import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record SSHRecordDto(@NotBlank String username, @NotBlank @NotNull String email,
                           @NotBlank @NotNull String publicKey) {

}
