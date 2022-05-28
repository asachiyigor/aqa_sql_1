package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.Data;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");


    public void checkIfPasswordErrorAppears() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка! Превышено количество попыток ввода пароля"));
    }

    public void checkIfCodeErrorAppears() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка"));
    }

    public void invalidLogin(Data.AuthInfo invalidInfo) {
        loginField.setValue(invalidInfo.getLogin());
        passwordField.setValue(invalidInfo.getPassword());
        loginButton.click();
    }

    public void sendAnInvalidPassword(Data.AuthInfo invalidInfo) {
        String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        passwordField.sendKeys(deleteString);
        passwordField.setValue(invalidInfo.getPassword());
        loginButton.click();
    }

    public void sendInvalidPasswordThreeTimes(Data.AuthInfo invalidInfo) {

        invalidLogin(invalidInfo);
        sendAnInvalidPassword(invalidInfo);
        sendAnInvalidPassword(invalidInfo);
    }

    public VerificationPage validLogin(Data.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}