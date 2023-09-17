package com.AuthSSH.ssh.dtos;

import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record RegisterDTO(
    @NotBlank(message = "usarname cannot be blank") @NotNull String username,
    @NotBlank(message = "cannot be blank") @NotNull String email,
    @NotBlank(message = "cannot be blank") @NotNull String publicKey
) {

}
