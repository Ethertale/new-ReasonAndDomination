package io.ethertale.reasonanddominationspringdefenseproject.account.repo;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, UUID> {

    List<Profile> findByUsernameContainingIgnoreCase(String username);

    Optional<Profile> findByUsername(String name);

    Optional<Profile> findByEmail(String email);

    boolean findByEmailAndPassword(String email, String password);

    Profile getProfileById(UUID id);
}
