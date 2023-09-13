package com.AuthSSH.ssh.repositories;

import com.AuthSSH.ssh.models.SshKey;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SshKeyRepository extends JpaRepository<SshKey, UUID> {

}
