package com.AuthSSH.ssh.services;

import com.AuthSSH.ssh.dtos.AuthenticateDTO;
import com.AuthSSH.ssh.models.SshKey;
import com.AuthSSH.ssh.models.User;
import com.AuthSSH.ssh.repositories.UserRepository;
import com.AuthSSH.ssh.utils.ServiceResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  public ServiceResponse checkSshKey(AuthenticateDTO authenticateDTO) throws IOException {
    Optional<User> userOptional = userRepository.findByEmail(authenticateDTO.email());

    if (userOptional.isEmpty()) {
      return new ServiceResponse(false, HttpStatus.NOT_FOUND, "User not found");
    }

    User user = userOptional.get();
    List<SshKey> sshKeys = user.getSshKeys();

    boolean keyFound = false;

    for (SshKey chaveSsh : sshKeys) {
      String chaveSshAtual = chaveSsh.getId().getPublicKey();

      if (chaveSshAtual.equals(authenticateDTO.publicKey())) {
        keyFound = true;
        break;
      }
    }

    if (!keyFound) {
      return new ServiceResponse(false, HttpStatus.UNAUTHORIZED, "Unauthorized publicKey");
    }

    return new ServiceResponse(true, HttpStatus.ACCEPTED, null);
  }
}
