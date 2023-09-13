package com.AuthSSH.ssh.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "SSH_key")
public class SshKey implements Serializable {

  @EmbeddedId
  private SshKeyPK id;

  @MapsId("userId")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public SshKeyPK getId() {
    return id;
  }

  public void setId(SshKeyPK id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "SshKey{" +
        "id=" + id +
        ", user=" + user +
        '}';
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Embeddable
  public static class SshKeyPK implements Serializable {

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "publicKey")
    private String publicKey;

    public UUID getUserId() {
      return userId;
    }

    public void setUserId(UUID userId) {
      this.userId = userId;
    }

    public String getPublicKey() {
      return publicKey;
    }

    public void setPublicKey(String publicKey) {
      this.publicKey = publicKey;
    }

    @Override
    public String toString() {
      return "SshKeyPK{" +
          "userId=" + userId +
          ", publicKey='" + publicKey + '\'' +
          '}';
    }
  }
}
