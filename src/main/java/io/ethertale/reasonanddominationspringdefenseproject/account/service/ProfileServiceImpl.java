package io.ethertale.reasonanddominationspringdefenseproject.account.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void registerProfile(String username, String password, String email, String confirmPassword) {

        checkForExceptionsRegister(username, password, email, confirmPassword);
        
        Profile profile = new Profile();
        profile.setUsername(username);
        profile.setPassword(passwordEncoder.encode(password));
        profile.setEmail(email);
        profile.setRole(AccountRole.USER);
        profile.setStatus(AccountStatus.ACTIVE);
        profile.setCreatedOn(LocalDateTime.now());

        profileRepo.save(profile);
    }

    private void checkForExceptionsRegister(String username, String password, String email, String confirmPassword) {
        if (username.length() < 5 || username.length() > 15 || username.isBlank()) {
            throw new RegisterUsernameTooShortException();
        }
        if (password.length() < 6 || password.isBlank()) {
            throw new RegisterPasswordTooShortException();
        }
        if (!emailRegexChecker(email) || email.isBlank()) {
            throw new RegisterInvalidEmailException();
        }
        if (!confirmPassword.equals(password) || confirmPassword.isBlank()) {
            throw new RegisterInvalidConfirmPasswordException();
        }
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepo.findAll();
    }

    @Override
    public List<Profile> getAllProfilesReversed() {
        return profileRepo.findAll().stream().sorted(Comparator.comparing(Profile::getCreatedOn).reversed()).toList();
    }

    public Profile loginProfile(FormLoginDTO formLoginDTO) {

        Optional<Profile> optionProfile = profileRepo.findByEmail(formLoginDTO.getEmail());
        if (optionProfile.isEmpty()) {
            throw new LoginProfileDoesNotExistException();
        }

        Profile profile = optionProfile.get();
        if (!passwordEncoder.matches(formLoginDTO.getPassword(), profile.getPassword())) {
            throw new LoginProfileWrongPasswordException();
        }
        if (profile.getStatus() == AccountStatus.DEACTIVATED) {
            throw new LoginProfileDeactivatedException();
        }

        return profile;
    }

    @Override
    public void updateProfile(EditProfile editProfile, Profile details) {
        Profile profileToEdit = profileRepo.getProfileById(details.getId());

        if (editProfile.getProfilePicture() == null || editProfile.getProfilePicture().isBlank()) {
            profileToEdit.setProfilePicture(details.getProfilePicture());
        } else {
            profileToEdit.setProfilePicture(editProfile.getProfilePicture());
        }

        if (editProfile.getRole() == null) {
            profileToEdit.setRole(profileToEdit.getRole());
        } else {
            profileToEdit.setRole(editProfile.getRole());
        }

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
    public List<AccountRole> getAllRoles() {
        return List.of(AccountRole.values());
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

    public boolean emailRegexChecker(String email) {
        Pattern emailPattern = Pattern.compile("^[\\w.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
