package io.ethertale.reasonanddominationspringdefenseproject.account;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileServiceImpl;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormRegisterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProfileITests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private ProfileServiceImpl profileService;

    @Test
    void registerUser_OK(){

        FormRegisterDTO formRegisterDTO = new FormRegisterDTO();
        formRegisterDTO.setUsername("username");
        formRegisterDTO.setPassword("password");
        formRegisterDTO.setEmail("email@mail.com");
        formRegisterDTO.setConfirmPassword("password");

        profileService.registerProfile(formRegisterDTO.getUsername(), formRegisterDTO.getPassword(), formRegisterDTO.getEmail(), formRegisterDTO.getConfirmPassword());

        Profile registeredProfile = profileRepo.findByEmail("email@mail.com").get();

        assertThat(registeredProfile).isNotNull();
        assertThat(registeredProfile.getUsername()).isEqualTo("username");
        assertThat(registeredProfile.getEmail()).isEqualTo("email@mail.com");
        assertThat(passwordEncoder.matches("password", registeredProfile.getPassword())).isTrue();
        assertThat(registeredProfile.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(registeredProfile.getRole()).isEqualTo(AccountRole.USER);

    }
    @Test
    void givenTwoUsers_SearchUsers_CorrectWord_ReturnsUserWithContainedWord() {
        Profile profile1 = Profile.builder()
                .username("RedTiger52")
                .email("redtiger@test.com")
                .password("password")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .createdOn(LocalDateTime.now())
                .build();

        Profile profile2 = Profile.builder()
                .username("OpalNeon61")
                .email("opalneon@test.com")
                .password("password")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .createdOn(LocalDateTime.now())
                .build();

        profileRepo.save(profile1);
        profileRepo.save(profile2);

        String input = "tige";
        List<Profile> returnedProfiles = profileService.searchUsers(input);

        // Then: Validate results
        assertThat(returnedProfiles).hasSize(1);
        assertThat(returnedProfiles.get(0).getUsername()).isEqualTo("RedTiger52");
    }
}
