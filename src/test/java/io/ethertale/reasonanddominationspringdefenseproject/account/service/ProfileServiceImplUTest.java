package io.ethertale.reasonanddominationspringdefenseproject.account.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterInvalidConfirmPasswordException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterInvalidEmailException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterPasswordTooShortException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterUsernameTooShortException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
    void loginProfile() {
    }

    @Test
    void profileExistsByUsername() {
    }

    @Test
    void profileExistsByEmail() {
    }

    @Test
    void updateProfile() {
    }

    @Test
    void updateProfileRole() {
    }

    @Test
    void getAllRoles() {
    }

    @Test
    void getProfileById() {
    }

    @Test
    void loadUserByUsername() {
    }
}
