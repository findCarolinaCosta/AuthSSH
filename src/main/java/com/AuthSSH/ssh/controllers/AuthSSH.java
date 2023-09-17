package com.AuthSSH.ssh.controllers;

import com.AuthSSH.ssh.dtos.AuthenticateDTO;
import com.AuthSSH.ssh.dtos.RegisterDTO;
import com.AuthSSH.ssh.models.SshKey;
import com.AuthSSH.ssh.models.User;
import com.AuthSSH.ssh.repositories.SshKeyRepository;
import com.AuthSSH.ssh.repositories.UserRepository;
import com.AuthSSH.ssh.services.AuthService;
import com.AuthSSH.ssh.utils.ServiceResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/AuthSSH/ssh")
public class AuthSSH {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SshKeyRepository sshKeyRepository;

  @Autowired
  private AuthService authService;

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
  public ResponseEntity<Void> authenticateSSH(@RequestBody @Valid AuthenticateDTO authenticateDTO, HttpServletResponse response)
      throws IOException {

    ServiceResponse serviceResponse = authService.checkSshKey(authenticateDTO);

    if (serviceResponse.isResult() == false) {
      response.sendError(serviceResponse.getStatus().value(), serviceResponse.getMessage());
    }

    return ResponseEntity.accepted().build();
  }
}
