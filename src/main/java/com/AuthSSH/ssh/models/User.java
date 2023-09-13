package com.AuthSSH.ssh.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_client")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID userId;

  @NotNull
  private String username;

  @NotNull
  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<SshKey> sshKeys;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(UUID uuid) {
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public List<SshKey> getSshKeys() {
    return sshKeys;
  }

  public void setSshKeys(List<SshKey> sshKeys) {
    this.sshKeys = sshKeys;
  }
}
