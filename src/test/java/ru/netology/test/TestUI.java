package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Data;
import ru.netology.data.DbInteraction;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class TestUI {

    private LoginPage loginPage;
    private static final DbInteraction db = new DbInteraction();
    private Data.AuthInfo userInfo;

    @BeforeEach
    private void setup() {
        Configuration.holdBrowserOpen = true;
        userInfo = Data.Registration.generateActiveUser();
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    public void shouldAuthorizationIsSuccessful() {
        db.addUser(userInfo);
        var verificationPage = loginPage.validLogin(userInfo);
        var verificationCode = db.getVerificationCode(userInfo);
        var dashboard = verificationPage.validVerify(verificationCode);
        dashboard.shouldBeVisible();
    }

    @Test
    public void shouldShowErrorIfEnterInvalidCode() {
        db.addUser(userInfo);
        var verificationPage = loginPage.validLogin(userInfo);
        var invalidVerificationCode = verificationPage.invalidVerificationCode();
        verificationPage.inValidVerify(invalidVerificationCode);
        loginPage.checkIfCodeErrorAppears();
    }

    @Test
    public void shouldLockIfAnInvalidPasswordIsEnteredThreeTimes() {
        loginPage.sendInvalidPasswordThreeTimes(userInfo);
        loginPage.checkIfPasswordErrorAppears();
    }
}