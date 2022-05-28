package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.val;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(visible, Duration.ofSeconds(2));
    }

    public void verify(String verificationCode) {
        codeField.val(verificationCode);
        verifyButton.click();
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }
    public DashboardPage inValidVerify(String invalidVerificationCode) {
        verify(invalidVerificationCode());
        return new DashboardPage();
    }

    public static String invalidVerificationCode() {
        val faker = new Faker();
        return faker.number().digits(5);
    }
}