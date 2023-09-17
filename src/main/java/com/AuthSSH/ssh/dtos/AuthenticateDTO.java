package com.AuthSSH.ssh.dtos;

import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record AuthenticateDTO(
    @NotBlank(message = "email cannot be blank") @NotNull String email,
    @NotBlank(message = "publicKey cannot be blank") @NotNull String publicKey
) {

}
