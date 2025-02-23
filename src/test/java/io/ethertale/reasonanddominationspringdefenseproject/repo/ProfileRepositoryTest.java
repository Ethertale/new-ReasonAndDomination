package io.ethertale.reasonanddominationspringdefenseproject.repo;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepo profileRepo;

    private static final String USERNAME_NORMAL = "TestName123";
    private static final String USERNAME_NULL = null;
    private static final String USERNAME_EMPTY = "";
    private static final String USERNAME_SPACE = " ";
    private static final String USERNAME_SPACED = "test Name";

    private static final String PASSWORD_NORMAL = "TestPassword123";
    private static final String PASSWORD_NULL = null;
    private static final String PASSWORD_EMPTY = "";
    private static final String PASSWORD_SPACE = " ";

    private static final String EMAIL_NORMAL = "TestEmail123@email.com";
    private static final String EMAIL_NULL = null;
    private static final String EMAIL_EMPTY = "";
    private static final String EMAIL_SPACE = " ";

    private static final AccountRole ROLE_USER = AccountRole.USER;
    private static final AccountRole ROLE_ADMIN = AccountRole.ADMIN;
    private static final AccountRole ROLE_NULL = null;

    private static final AccountStatus STATUS_ACTIVE = AccountStatus.ACTIVE;
    private static final AccountStatus STATUS_DEACTIVATED = AccountStatus.DEACTIVATED;
    private static final AccountStatus STATUS_NULL = null;

    private static final String PROFILE_PICTURE_NORMAL = "testLinkProfilePicture.png";
    private static final String PROFILE_PICTURE_NULL = null;
    private static final String PROFILE_PICTURE_EMPTY = "";
    private static final String PROFILE_PICTURE_SPACE = " ";
    private static final String PROFILE_PICTURE_NOT_VALID_FORMAT = "testWrongFormat.j";

    @Test
    public void profileRepository_SaveProfile_ReturnSavedProfile() {

        //Arrange
        Profile profileTest = Profile.builder()
                .username(USERNAME_NORMAL)
                .password("123123")
                .email("test@a.com")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("ProfilePic")
                .createdOn(LocalDateTime.now())
                .build();

        //Act
        Profile savedProfile = profileRepo.save(profileTest);

        //Assert
        Assertions.assertThat(savedProfile).isNotNull();
        Assertions.assertThat(savedProfile.getId()).isNotNull();
    }

    @Test
    public void profileRepository_SaveProfile_NullName_ShouldThrowException() {

        Profile profileNullName = Profile.builder()
                .username(USERNAME_NULL)
                .password(PASSWORD_NORMAL)
                .email(EMAIL_NORMAL)
                .role(ROLE_USER)
                .status(STATUS_ACTIVE)
                .profilePicture(PROFILE_PICTURE_NORMAL)
                .createdOn(LocalDateTime.now())
                .build();

        Assertions.assertThatThrownBy(() -> {
            profileRepo.save(profileNullName);
            profileRepo.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    public void profileRepository_saveProfile_EmptyName_ShouldThrowException() {
        Profile profileEmptyName = Profile.builder()
                .username(USERNAME_EMPTY)
                .password(PASSWORD_NORMAL)
                .email(EMAIL_NORMAL)
                .role(ROLE_USER)
                .status(STATUS_ACTIVE)
                .profilePicture(PROFILE_PICTURE_NORMAL)
                .createdOn(LocalDateTime.now())
                .build();

        Assertions.assertThatThrownBy(() -> {
            profileRepo.save(profileEmptyName);
            profileRepo.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void profileRepository_saveProfile_EmptySpaceName_ShouldThrowException() {
        Profile profileEmptySpaceName = Profile.builder()
                .username(USERNAME_SPACE)
                .password(PASSWORD_NORMAL)
                .email(EMAIL_NORMAL)
                .role(ROLE_USER)
                .status(STATUS_ACTIVE)
                .profilePicture(PROFILE_PICTURE_NORMAL)
                .createdOn(LocalDateTime.now())
                .build();

        Assertions.assertThatThrownBy(() -> {
            profileRepo.save(profileEmptySpaceName);
            profileRepo.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void profileRepository_saveProfile_NameWithSpaces_ShouldThrowException() {
        Profile profileNameWithSpaces = Profile.builder()
                .username(USERNAME_SPACED)
                .password(PASSWORD_NORMAL)
                .email(EMAIL_NORMAL)
                .role(ROLE_USER)
                .status(STATUS_ACTIVE)
                .profilePicture(PROFILE_PICTURE_NORMAL)
                .createdOn(LocalDateTime.now())
                .build();


        Assertions.assertThatThrownBy(() -> {
            profileRepo.save(profileNameWithSpaces);
            profileRepo.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}
