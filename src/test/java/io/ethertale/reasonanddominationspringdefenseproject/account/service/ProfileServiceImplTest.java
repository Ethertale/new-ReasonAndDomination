package io.ethertale.reasonanddominationspringdefenseproject.account.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterUsernameTooShortException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

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

//    @Test
//    void getAllProfiles() {
//    }
//
//    @Test
//    void getAllProfilesReversed() {
//    }
//
//    @Test
//    void loginProfile() {
//    }
//
//    @Test
//    void profileExistsByUsername() {
//    }
//
//    @Test
//    void profileExistsByEmail() {
//    }
//
//    @Test
//    void updateProfile() {
//    }
//
//    @Test
//    void updateProfileRole() {
//    }
//
//    @Test
//    void getAllRoles() {
//    }
//
//    @Test
//    void getProfileById() {
//    }
//
//    @Test
//    void loadUserByUsername() {
//    }
}