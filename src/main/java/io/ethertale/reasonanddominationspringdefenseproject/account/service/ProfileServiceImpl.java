package io.ethertale.reasonanddominationspringdefenseproject.account.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.EditProfile;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProfileServiceImpl implements ProfileService, UserDetailsService {
    ProfileRepo profileRepo;
    PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileServiceImpl(ProfileRepo profileRepo, PasswordEncoder passwordEncoder) {
        this.profileRepo = profileRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Profile createTestUser() {
        return null;
    }

    @Override
    public void registerProfile(String username, String password, String email) {
        Profile profile = new Profile();
        profile.setUsername(username);
        profile.setPassword(passwordEncoder.encode(password));
        profile.setEmail(email);
        profile.setRole(AccountRole.USER);
        profile.setStatus(AccountStatus.ACTIVE);
        profile.setCreatedOn(LocalDateTime.now());
        profileRepo.save(profile);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepo.findAll();
    }

    @Override
    public List<Profile> getAllProfilesReversed() {
        return profileRepo.findAll().stream().sorted(Comparator.comparing(Profile::getCreatedOn).reversed()).toList();
    }

    public Profile loginProfile(FormLoginDTO formLoginDTO){

        Optional<Profile> optionProfile = profileRepo.findByEmail(formLoginDTO.getEmail());
        if (optionProfile.isEmpty()){
            throw new IllegalArgumentException("Invalid email or password");
        }

        Profile profile = optionProfile.get();
        if (!passwordEncoder.matches(formLoginDTO.getPassword(), profile.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }
        if (profile.getStatus() == AccountStatus.DEACTIVATED){
            throw new IllegalArgumentException("Account is deactivated");
        }

        return profile;
    }

    @Override
    public boolean profileExistsByUsername(String username) {
        return profileRepo.existsByUsername(username);
    }

    @Override
    public boolean profileExistsByEmail(String email) {
        return profileRepo.existsByEmail(email);
    }

    @Override
    public void updateProfile(EditProfile editProfile, AuthenticationDetails details) {
        Profile profileToEdit = profileRepo.getProfileById(details.getId());

        profileToEdit.setProfilePicture(editProfile.getProfilePicture());

        profileRepo.save(profileToEdit);
    }

    @Override
    public Profile updateProfileRole(UUID id, String tier) {
        Profile profile = getProfileById(id);
        switch (tier) {
            case "uncommon":
                profile.setRole(AccountRole.TIER_UNCOMMON);
                break;
            case "rare":
                profile.setRole(AccountRole.TIER_RARE);
                break;
            case "epic":
                profile.setRole(AccountRole.TIER_EPIC);
                break;
            case "legendary":
                profile.setRole(AccountRole.TIER_LEGENDARY);
                break;
        }
        return profileRepo.save(profile);
    }


    @Override
    public Profile getProfileById(UUID uuid) {
        return profileRepo.findById(uuid).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Profile profile = profileRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        return new AuthenticationDetails(profile.getId(), profile.getEmail(), profile.getPassword(), profile.getRole(), true, profile.getProfilePicture());
    }
}
