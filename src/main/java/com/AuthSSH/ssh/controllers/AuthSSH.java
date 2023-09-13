package com.AuthSSH.ssh.controllers;

import com.AuthSSH.ssh.dtos.SSHRecordDto;
import com.AuthSSH.ssh.models.SshKey;
import com.AuthSSH.ssh.models.User;
import com.AuthSSH.ssh.repositories.SshKeyRepository;
import com.AuthSSH.ssh.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/register")
  public ResponseEntity<Void> registerSSH(@RequestBody @Valid SSHRecordDto sshRecordDto) {

    var userModel = new User();

    BeanUtils.copyProperties(sshRecordDto, userModel);

    User userSaved = userRepository.save(userModel);

    SshKey sshKey = new SshKey();
    SshKey.SshKeyPK sshKeyPK = new SshKey.SshKeyPK();

    sshKeyPK.setUserId(userSaved.getUserId());
    sshKeyPK.setPublicKey(sshRecordDto.publicKey());

    sshKey.setId(sshKeyPK);

    sshKey.setUser(userSaved);

    sshKeyRepository.save(sshKey);

    return ResponseEntity.accepted().build();
  }
}
