package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

public class Data {

    public Data() {
    }

    @Value
    public static class AuthInfo {
        String id;
        String login;
        String password;
        String status;
    }

    public static String generateId() {
        val faker = new Faker();
        return faker.regexify("[A-Za-z0-9]{36}");
    }

    public static String generateLogin() {
        val faker = new Faker();
        return faker.name().username();
    }

    public static String generatePassword() {
        val faker = new Faker();
        return faker.internet().password(true);
    }

    public static class Registration {
        private Registration() {
        }

        public static AuthInfo generateActiveUser() {
            return new AuthInfo(generateId(), generateLogin(), generatePassword(), "active");
        }
    }
}