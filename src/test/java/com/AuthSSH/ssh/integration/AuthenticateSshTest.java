package com.AuthSSH.ssh.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.AuthSSH.ssh.models.User;
import com.AuthSSH.ssh.repositories.SshKeyRepository;
import com.AuthSSH.ssh.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
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
@AutoConfigureMockMvc
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

  @BeforeAll
  public void beforeAll() {
    configureMockUserRepository();
  }

  private void configureMockUserRepository() {
    when(userRepository.findByEmail(any(String.class))).thenAnswer(invocation -> {
      String email = invocation.getArgument(0);
      User user = new User();
      user.setId(UUID.randomUUID());
      user.setEmail(email);

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
}
