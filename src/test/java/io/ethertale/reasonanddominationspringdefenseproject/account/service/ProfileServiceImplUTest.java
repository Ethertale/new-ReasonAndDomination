package io.ethertale.reasonanddominationspringdefenseproject.account.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.*;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.EditProfile;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormLoginDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplUTest {

    @Mock
    private ProfileRepo profileRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void testProfileGetSet(){
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID());
        profile.setUsername("username");
        profile.setPassword("password");
        profile.setEmail("email@mail.com");
        profile.setRole(AccountRole.USER);
        profile.setStatus(AccountStatus.ACTIVE);
        profile.setProfilePicture("picture");
        profile.setHeroes(new ArrayList<>());
        profile.setPosts(new ArrayList<>());
        profile.setComments(new ArrayList<>());
        profile.setCreatedOn(LocalDateTime.now());

        profileRepo.save(profile);


    }

    @Test
    void givenUserToRegister_whenRegister_thenExpectOk() {
        // Given
        String username = "RedTiger52";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // When
        profileService.registerProfile(username, rawPassword, email, confirmPassword);

        // Then
        ArgumentCaptor<Profile> profileCaptor = ArgumentCaptor.forClass(Profile.class);
        verify(profileRepo, times(1)).save(profileCaptor.capture());

        Profile savedProfile = profileCaptor.getValue();
        assertThat(savedProfile.getUsername()).isEqualTo(username);
        assertThat(savedProfile.getEmail()).isEqualTo(email);
        assertThat(savedProfile.getPassword()).isEqualTo(encodedPassword);
        assertThat(savedProfile.getRole()).isEqualTo(AccountRole.USER);
        assertThat(savedProfile.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(savedProfile.getCreatedOn()).isNotNull();
    }
    @Test
    void givenUserToRegisterInvalidUsernameShort_whenRegister_thenExpectException() {
        // Given
        String username = "1r";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterUsernameTooShortException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterEmptyUsername_whenRegister_thenExpectException() {
        // Given
        String username = "";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterUsernameTooShortException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterBlankUsername_whenRegister_thenExpectException() {
        // Given
        String username = "   ";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterUsernameTooShortException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidUsernameLong_whenRegister_thenExpectException() {
        // Given
        String username = "TestTestTestTest";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterUsernameTooShortException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidPasswordShort_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "123";
        String confirmPassword = "123";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterPasswordTooShortException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidPasswordEmpty_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "";
        String confirmPassword = "";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterPasswordTooShortException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidPasswordBlank_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "   ";
        String confirmPassword = "   ";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterPasswordTooShortException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidEmail_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "redtiger52";

        // When & Then
        assertThrows(RegisterInvalidEmailException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidEmailEmpty_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "";

        // When & Then
        assertThrows(RegisterInvalidEmailException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidEmailBlank_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "123123";
        String confirmPassword = "123123";
        String encodedPassword = "encoded_password";
        String email = "   ";

        // When & Then
        assertThrows(RegisterInvalidEmailException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidConfirmPassword_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "123123";
        String confirmPassword = "123456";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterInvalidConfirmPasswordException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidConfirmPasswordEmpty_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "123123";
        String confirmPassword = "";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterInvalidConfirmPasswordException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void givenUserToRegisterInvalidConfirmPasswordBlank_whenRegister_thenExpectException() {
        // Given
        String username = "TestTest";
        String rawPassword = "123123";
        String confirmPassword = "   ";
        String encodedPassword = "encoded_password";
        String email = "redtiger52@testmail.com";

        // When & Then
        assertThrows(RegisterInvalidConfirmPasswordException.class,
                () -> profileService.registerProfile(username, rawPassword, email, confirmPassword)
        );
    }
    @Test
    void getAllProfiles_GivenTwoProfilesSavedToRepo_ThenExpectReturnOk() {
        //Given
        Profile profile1 = Profile.builder()
                .id(UUID.randomUUID())
                .username("Test1")
                .profilePicture("profpic")
                .role(AccountRole.USER)
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .comments(new ArrayList<>())
                .status(AccountStatus.ACTIVE)
                .password("123123")
                .build();
        Profile profile2 = Profile.builder()
                .id(UUID.randomUUID())
                .username("Test2")
                .profilePicture("profpic")
                .role(AccountRole.USER)
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .comments(new ArrayList<>())
                .status(AccountStatus.ACTIVE)
                .password("123123")
                .build();

        profileRepo.save(profile1);
        profileRepo.save(profile2);

        when(profileRepo.findAll()).thenReturn(List.of(profile1, profile2));

        List<Profile> profiles = profileService.getAllProfiles();

        assertThat(profiles).hasSize(2);
        assertThat(profiles).contains(profile1, profile2);
    }
    @Test
    void getAllProfilesReversed_GivenTwoProfilesSavedToRepo_ThenExpectReturnOk() {
        //Given
        Profile profile1 = Profile.builder()
                .id(UUID.randomUUID())
                .username("Test1")
                .profilePicture("profpic")
                .role(AccountRole.USER)
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .comments(new ArrayList<>())
                .status(AccountStatus.ACTIVE)
                .password("123123")
                .build();
        Profile profile2 = Profile.builder()
                .id(UUID.randomUUID())
                .username("Test2")
                .profilePicture("profpic")
                .role(AccountRole.USER)
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .comments(new ArrayList<>())
                .status(AccountStatus.ACTIVE)
                .password("123123")
                .build();

        profileRepo.save(profile1);
        profileRepo.save(profile2);

        when(profileRepo.findAll().stream().sorted(Comparator.comparing(Profile::getCreatedOn).reversed()).toList()).thenReturn(List.of(profile2, profile1));

        List<Profile> profiles = profileService.getAllProfilesReversed();

        assertThat(profiles).hasSize(2);
        assertThat(profiles).contains(profile2, profile1);
    }
    @Test
    void givenCorrectUser_loginProfile_ShouldReturnOk() {
        Profile user = Profile.builder()
                .id(UUID.randomUUID())
                .username("RedTiger52")
                .password("encodedPassword")
                .email("redtiger52@testmail.com")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("profpic")
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();

        FormLoginDTO formLoginDTO = new FormLoginDTO("redtiger52@testmail.com", "123123");

        when(profileRepo.findByEmail(formLoginDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(formLoginDTO.getPassword(), user.getPassword())).thenReturn(true);

        Profile loggedProfile = profileService.loginProfile(formLoginDTO);

        assertThat(loggedProfile).isEqualTo(user);
    }
    @Test
    void givenNonExistentUser_loginProfile_ShouldThrowException() {
        Profile user = Profile.builder()
                .id(UUID.randomUUID())
                .username("RedTiger52")
                .password("encodedPassword")
                .email("redtiger52@testmail.com")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("profpic")
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();

        FormLoginDTO formLoginDTO = new FormLoginDTO("redtiger52@testmail.com", "123123");

        when(profileRepo.findByEmail(formLoginDTO.getEmail())).thenReturn(Optional.empty());

        assertThrows(LoginProfileDoesNotExistException.class, () -> profileService.loginProfile(formLoginDTO));
    }
    @Test
    void givenUserWithWrongPassword_loginProfile_ShouldThrowException() {
        Profile user = Profile.builder()
                .id(UUID.randomUUID())
                .username("RedTiger52")
                .password("encodedPassword")
                .email("redtiger52@testmail.com")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("profpic")
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();

        FormLoginDTO formLoginDTO = new FormLoginDTO("redtiger52@testmail.com", "123123");

        when(profileRepo.findByEmail(formLoginDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(formLoginDTO.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(LoginProfileWrongPasswordException.class, () -> profileService.loginProfile(formLoginDTO));
    }
    @Test
    void givenUserWithDeactivatedAccount_loginProfile_ShouldThrowException() {
        Profile user = Profile.builder()
                .id(UUID.randomUUID())
                .username("RedTiger52")
                .password("encodedPassword")
                .email("redtiger52@testmail.com")
                .role(AccountRole.USER)
                .status(AccountStatus.DEACTIVATED)
                .profilePicture("profpic")
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();

        FormLoginDTO formLoginDTO = new FormLoginDTO("redtiger52@testmail.com", "123123");

        when(profileRepo.findByEmail(formLoginDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(formLoginDTO.getPassword(), user.getPassword())).thenReturn(true);

        assertThrows(LoginProfileDeactivatedException.class, () -> profileService.loginProfile(formLoginDTO));
    }
    @Test
    void givenUser_NewProfilePicture_NoNewRole_updateProfile_ShouldReturnOk() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .profilePicture("oldPic.png")
                .role(AccountRole.USER)
                .build();

        EditProfile editProfile = new EditProfile();
        editProfile.setProfilePicture("newPic.png");
        editProfile.setRole(null);

        when(profileRepo.getProfileById(profileId)).thenReturn(existingProfile);

        profileService.updateProfile(editProfile, existingProfile);

        assertThat(existingProfile.getProfilePicture()).isEqualTo("newPic.png");
        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_NoNewProfilePictureEmpty_NoNewRole_updateProfile_ShouldReturnOk() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .profilePicture("oldPic.png")
                .role(AccountRole.USER)
                .build();

        EditProfile editProfile = new EditProfile();
        editProfile.setProfilePicture("");
        editProfile.setRole(null);

        when(profileRepo.getProfileById(profileId)).thenReturn(existingProfile);

        profileService.updateProfile(editProfile, existingProfile);

        assertThat(existingProfile.getProfilePicture()).isEqualTo("oldPic.png");
        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_NoNewProfilePicture_NewRole_updateProfile_ShouldReturnOk() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .profilePicture("oldPic.png")
                .role(AccountRole.USER)
                .build();

        EditProfile editProfile = new EditProfile();
        editProfile.setProfilePicture(null);
        editProfile.setRole(AccountRole.ADMIN);

        when(profileRepo.getProfileById(profileId)).thenReturn(existingProfile);

        profileService.updateProfile(editProfile, existingProfile);

        assertThat(existingProfile.getRole()).isEqualTo(AccountRole.ADMIN);
        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_NoNewProfilePictureNull_NoNewRole_updateProfile_ShouldReturnOk() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .profilePicture("oldPic.png")
                .role(AccountRole.USER)
                .build();

        EditProfile editProfile = new EditProfile();
        editProfile.setProfilePicture(null);
        editProfile.setRole(null);

        when(profileRepo.getProfileById(profileId)).thenReturn(existingProfile);

        profileService.updateProfile(editProfile, existingProfile);

        assertThat(existingProfile.getRole()).isEqualTo(AccountRole.USER);
        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_SupportPage_updateProfileRole_Uncommon() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .role(AccountRole.USER)
                .build();

        when(profileRepo.findById(profileId)).thenReturn(Optional.of(existingProfile));

        profileService.updateProfileRole(profileId, "uncommon");

        assertThat(existingProfile.getRole()).isEqualTo(AccountRole.TIER_UNCOMMON);

        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_SupportPage_updateProfileRole_Rare() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .role(AccountRole.USER)
                .build();

        when(profileRepo.findById(profileId)).thenReturn(Optional.of(existingProfile));

        profileService.updateProfileRole(profileId, "rare");

        assertThat(existingProfile.getRole()).isEqualTo(AccountRole.TIER_RARE);

        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_SupportPage_updateProfileRole_Epic() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .role(AccountRole.USER)
                .build();

        when(profileRepo.findById(profileId)).thenReturn(Optional.of(existingProfile));

        profileService.updateProfileRole(profileId, "epic");

        assertThat(existingProfile.getRole()).isEqualTo(AccountRole.TIER_EPIC);

        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_SupportPage_updateProfileRole_Legendary() {
        UUID profileId = UUID.randomUUID();
        Profile existingProfile = Profile.builder()
                .id(profileId)
                .role(AccountRole.USER)
                .build();

        when(profileRepo.findById(profileId)).thenReturn(Optional.of(existingProfile));

        profileService.updateProfileRole(profileId, "legendary");

        assertThat(existingProfile.getRole()).isEqualTo(AccountRole.TIER_LEGENDARY);

        verify(profileRepo).save(existingProfile);
    }
    @Test
    void givenUser_getProfileById_ShouldReturnOk() {
        Profile user = Profile.builder()
                .id(UUID.randomUUID())
                .username("RedTiger52")
                .password("encodedPassword")
                .email("redtiger52@testmail.com")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("profpic")
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();

        profileRepo.save(user);

        when(profileRepo.findById(user.getId())).thenReturn(Optional.of(user));

        Profile profile = profileService.getProfileById(user.getId());

        assertThat(profileRepo.findById(profile.getId()).isPresent());
    }
    @Test
    void givenNonExistentUser_getProfileById_ShouldReturnException() {
        Profile user = Profile.builder()
                .id(UUID.randomUUID())
                .username("RedTiger52")
                .password("encodedPassword")
                .email("redtiger52@testmail.com")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("profpic")
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();

        profileRepo.save(user);

        when(profileRepo.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(LoginProfileDoesNotExistException.class, () -> profileService.getProfileById(user.getId()));
    }
    @Test
    void givenExistingEmail_loadUserByUsername_ShouldReturnUserDetails() {
        Profile user = Profile.builder()
                .id(UUID.randomUUID())
                .username("RedTiger52")
                .password("encodedPassword")
                .email("redtiger52@testmail.com")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("profpic")
                .heroes(new ArrayList<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();

        when(profileRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = profileService.loadUserByUsername(user.getEmail());

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(user.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());

        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());

        assertThat(authorities).contains(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Test
    void givenNonExistingEmail_loadUserByUsername_ShouldThrowException() {
        String email = "nonexistent@example.com";

        when(profileRepo.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> profileService.loadUserByUsername(email));
    }
    @Test
    void getAllRoles_ShouldReturnAllRoles() {
        List<AccountRole> roles = profileService.getAllRoles();

        assertThat(roles).containsExactly(AccountRole.values());
    }
}


