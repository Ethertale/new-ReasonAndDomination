package io.ethertale.reasonanddominationspringdefenseproject.account.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormLoginDTO;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormRegisterDTO;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
    Profile createTestUser();
    void registerProfile(String username, String password, String email);
    List<Profile> getAllProfiles();
    List<Profile> getAllProfilesReversed();
    Profile getProfileById(UUID uuid);
    Profile loginProfile(FormLoginDTO formLoginDTO);
    boolean profileExistsByUsername(String username);
    boolean profileExistsByEmail(String email);
}
