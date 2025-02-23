package io.ethertale.reasonanddominationspringdefenseproject.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RegisterControllerTest {

    @Autowired
    private ProfileService profileService;

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
    public void RegisterController_RegisterUser_NullName_ShouldThrowException() {

    }
}
