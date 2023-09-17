package com.AuthSSH.ssh.integration;

import static org.mockito.Mockito.when;

import com.AuthSSH.ssh.models.SshKey;
import com.AuthSSH.ssh.models.User;
import com.AuthSSH.ssh.repositories.SshKeyRepository;
import com.AuthSSH.ssh.repositories.UserRepository;
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

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test") // for use application-test.properties with h2 database
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@DisplayName("Integration tests for Register SSH")
public class RegisterSshTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private SshKeyRepository sshKeyRepository;

  @BeforeAll
  public void beforeAll() {
    configureMockUserRepository();
    configureMockSshKeyRepository();
  }


  private void configureMockUserRepository() {
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      User user = invocation.getArgument(0);
      user.setUserId(UUID.randomUUID());
      return user;
    });
  }

  private void configureMockSshKeyRepository() {
    when(sshKeyRepository.save(any(SshKey.class))).thenAnswer(invocation -> {
      SshKey sshKey = invocation.getArgument(0);

      SshKey.SshKeyPK sshKeyPK = sshKey.getId();
      sshKeyPK.setUserId(UUID.randomUUID());
      return sshKey;
    });
  }

  @Test
  @DisplayName("Register SSH")
  public void registerSSH() throws Exception {

    String bodyContent = "{\"username\": \"username\", \"email\": \"email\", \"publicKey\": \"publicKey\"}";

    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/AuthSSH/ssh/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
    ).andExpect(MockMvcResultMatchers.status().isAccepted());
  }

  @Test
  @DisplayName("Register SSH - Invalid username")
  public void registerInvalidUsername() throws Exception {
    String bodyContent = "{\"username\": \"\", \"email\": \"email\", \"publicKey\": \"publicKey\"}";

    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/AuthSSH/ssh/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
    ).andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Register SSH - Invalid email")
  public void registerInvalidEmail() throws Exception {
    String bodyContent = "{\"username\": \"username\", \"email\": \"\", \"publicKey\": \"publicKey\"}";

    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/AuthSSH/ssh/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
    ).andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("Register SSH - Invalid public key")
  public void registerInvalidPublicKey() throws Exception {
    String bodyContent = "{\"username\": \"username\", \"email\": \"email\", \"publicKey\": \"\"}";

    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/AuthSSH/ssh/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyContent)
    ).andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
