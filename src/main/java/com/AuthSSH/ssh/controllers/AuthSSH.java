package com.AuthSSH.ssh.controllers;

import com.AuthSSH.ssh.dtos.AuthenticateDTO;
import com.AuthSSH.ssh.dtos.RegisterDTO;
import com.AuthSSH.ssh.models.SshKey;
import com.AuthSSH.ssh.models.User;
import com.AuthSSH.ssh.repositories.SshKeyRepository;
import com.AuthSSH.ssh.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/AuthSSH/ssh")
public class AuthSSH {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SshKeyRepository sshKeyRepository;

  @PostMapping("/register")
  public ResponseEntity<Void> registerSSH(@RequestBody @Valid RegisterDTO registerDTO) {

    var userModel = new User();

    BeanUtils.copyProperties(registerDTO, userModel);

    User userSaved = userRepository.save(userModel);

    SshKey sshKey = new SshKey();
    SshKey.SshKeyPK sshKeyPK = new SshKey.SshKeyPK();

    sshKeyPK.setUserId(userSaved.getUserId());
    sshKeyPK.setPublicKey(registerDTO.publicKey());

    sshKey.setId(sshKeyPK);

    sshKey.setUser(userSaved);

    sshKeyRepository.save(sshKey);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/authenticate")
  public ResponseEntity<Void> authenticateSSH(@RequestBody @Valid AuthenticateDTO authenticateDTO) {

    userRepository.findByEmail(authenticateDTO.email())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    return ResponseEntity.accepted().build();
  }
}
