package io.ethertale.reasonanddominationspringdefenseproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileServiceImpl;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormLoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepo profileRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private Profile profile;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setId(UUID.randomUUID());
        profile.setUsername("testUser");
        profile.setEmail("test@example.com");
        profile.setPassword("encodedPassword");
        profile.setRole(AccountRole.USER);
        profile.setStatus(AccountStatus.ACTIVE);
        profile.setCreatedOn(LocalDateTime.now());
    }

    @Test
    void testRegisterProfile() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        profileService.registerProfile("testUser", "password", "test@example.com");
        verify(profileRepo, times(1)).save(any(Profile.class));
    }

    @Test
    void testGetAllProfiles() {
        when(profileRepo.findAll()).thenReturn(List.of(profile));
        List<Profile> profiles = profileService.getAllProfiles();
        assertEquals(1, profiles.size());
        assertEquals("testUser", profiles.get(0).getUsername());
    }

    @Test
    void testGetAllProfilesReversed() {
        when(profileRepo.findAll()).thenReturn(List.of(profile));
        List<Profile> profiles = profileService.getAllProfilesReversed();
        assertEquals(1, profiles.size());
    }

    @Test
    void testLoginProfile_Success() {
        FormLoginDTO loginDTO = new FormLoginDTO("test@example.com", "password");
        when(profileRepo.findByEmail("test@example.com")).thenReturn(Optional.of(profile));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        Profile loggedInProfile = profileService.loginProfile(loginDTO);
        assertEquals("testUser", loggedInProfile.getUsername());
    }

    @Test
    void testLoginProfile_InvalidEmail() {
        FormLoginDTO loginDTO = new FormLoginDTO("wrong@example.com", "password");
        when(profileRepo.findByEmail("wrong@example.com")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> profileService.loginProfile(loginDTO));
    }

    @Test
    void testLoginProfile_InvalidPassword() {
        FormLoginDTO loginDTO = new FormLoginDTO("test@example.com", "wrongpassword");
        when(profileRepo.findByEmail("test@example.com")).thenReturn(Optional.of(profile));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> profileService.loginProfile(loginDTO));
    }

    @Test
    void testProfileExistsByUsername() {
        when(profileRepo.existsByUsername("testUser")).thenReturn(true);
        assertTrue(profileService.profileExistsByUsername("testUser"));
    }

    @Test
    void testProfileExistsByEmail() {
        when(profileRepo.existsByEmail("test@example.com")).thenReturn(true);
        assertTrue(profileService.profileExistsByEmail("test@example.com"));
    }

//    @Test
//    void testUpdateProfile() {
//        EditProfile editProfile = new EditProfile();
//        editProfile.setProfilePicture("newPic.png");
//        AuthenticationDetails details = new AuthenticationDetails(profile.getId(), "test@example.com", "encodedPassword", AccountRole.USER, true, "oldPic.png");
//
//        when(profileRepo.getProfileById(profile.getId())).thenReturn(profile);
//        profileService.updateProfile(editProfile, details);
//        assertEquals("newPic.png", profile.getProfilePicture());
//        verify(profileRepo, times(1)).save(profile);
//    }
}
