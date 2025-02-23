package io.ethertale.reasonanddominationspringdefenseproject.account.repo;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, UUID> {

    boolean findByUsername(String name);

    Optional<Profile> findByEmail(String email);

    boolean findByEmailAndPassword(String email, String password);

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
