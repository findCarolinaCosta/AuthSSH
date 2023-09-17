package com.AuthSSH.ssh.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.AuthSSH.ssh.models.SshKey;
import com.AuthSSH.ssh.models.SshKey.SshKeyPK;
import com.AuthSSH.ssh.models.User;
import com.AuthSSH.ssh.repositories.SshKeyRepository;
import com.AuthSSH.ssh.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@DisplayName("Integration tests for Authenticate SSH")
public class AuthenticateSshTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private SshKeyRepository sshKeyRepository;

  @BeforeEach
  public void beforeEach() {
    configureMockUserRepository();
  }

  @AfterEach
  public void afterEach() {
    reset(userRepository);
  }

  private void configureMockUserRepository() {
    when(userRepository.findByEmail(any(String.class))).thenAnswer(invocation -> {
      String email = invocation.getArgument(0);
      User user = new User();
      user.setUserId(UUID.randomUUID());
      user.setEmail(email);

      List<SshKey> sshKeys = new ArrayList<>();

      List<String> publicKeys = List.of("publicKey");

      for (String publicKey : publicKeys) {
        SshKey sshKey = new SshKey();
        SshKeyPK sshKeyPK = new SshKeyPK();
        sshKeyPK.setPublicKey(publicKey);
        sshKey.setUser(user);
        sshKey.setId(sshKeyPK);
        sshKeys.add(sshKey);
      }

      user.setSshKeys(sshKeys);

      Optional<User> optionalUser = Optional.of(user);

      return optionalUser;
    });
  }

  @Test
  @DisplayName("Should return 200 when authenticate with valid credentials")
  public void shouldReturn200WhenAuthenticateWithValidCredentials() throws Exception {
    String bodyContent = "{"
        + " \"email\": \"email\", "
        + "\"publicKey\": \"publicKey\""
        + "}";
    mockMvc.perform(MockMvcRequestBuilders
            .post("/AuthSSH/ssh/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
        )
        .andExpect(MockMvcResultMatchers.status().isAccepted());
  }

  @Test
  @DisplayName("Should return 404 when authenticate with invalid credentials")
  public void shouldReturn400WhenAuthenticateWithInvalidCredentials() throws Exception {

    when(userRepository.findByEmail(any(String.class)))
        .thenReturn(Optional.empty());

    String bodyContent = "{"
        + " \"email\": \"emailquenãoexiste\", "
        + "\"publicKey\": \"publicKey\""
        + "}";
    mockMvc.perform(MockMvcRequestBuilders
            .post("/AuthSSH/ssh/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
        )
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @DisplayName("Should return 400 when authenticate with invalid email")
  public void shouldReturn400WhenAuthenticateWithInvalidEmail() throws Exception {
    String bodyContent = "{"
        + " \"email\": \"\", "
        + "\"publicKey\": \"publicKey\""
        + "}";
    mockMvc.perform(MockMvcRequestBuilders
            .post("/AuthSSH/ssh/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 401 when authenticate with invalid public key")
  public void shouldReturn401WhenAuthenticateWithInvalidPublicKey() throws Exception {
    String bodyContent = "{"
        + " \"email\": \"email\", "
        + "\"publicKey\": \"invalidKey\""
        + "}";
    mockMvc.perform(MockMvcRequestBuilders
            .post("/AuthSSH/ssh/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
        )
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }
}
