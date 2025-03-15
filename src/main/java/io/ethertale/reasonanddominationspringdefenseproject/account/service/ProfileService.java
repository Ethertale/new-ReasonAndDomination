package io.ethertale.reasonanddominationspringdefenseproject.account.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.EditProfile;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormLoginDTO;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
    void registerProfile(String username, String password, String email, String confirmPassword);

    List<Profile> getAllProfiles();

    List<Profile> getAllProfilesReversed();

    Profile getProfileById(UUID uuid);

    Profile loginProfile(FormLoginDTO formLoginDTO);

    boolean profileExistsByUsername(String username);

    boolean profileExistsByEmail(String email);

    void updateProfile(EditProfile editProfile, Profile details);

    Profile updateProfileRole(UUID id, String tier);

    List<AccountRole> getAllRoles();
}
