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
import lombok.Data;

@Entity
@Table(name = "SSH_key")
@Data
public class SshKey implements Serializable {

  @EmbeddedId
  private SshKeyPK id;

  @MapsId("userId")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Embeddable
  @Data
  public static class SshKeyPK implements Serializable {

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "publicKey")
    private String publicKey;
  }
}
