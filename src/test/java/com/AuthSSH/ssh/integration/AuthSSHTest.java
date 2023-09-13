package com.AuthSSH.ssh.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // for use application-test.properties with h2 database
public class AuthSSHTest {

  @Autowired
  private MockMvc mockMvc;

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
