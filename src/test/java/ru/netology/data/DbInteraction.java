package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbInteraction {

    @SneakyThrows
    private Connection getConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "admin", "pass");
    }

    private String passwordEncryption(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @SneakyThrows
    public void addUser(Data.AuthInfo user) {
        var runner = new QueryRunner();
        var dataSQL = "INSERT INTO users(id, login, password, status) VALUES (?, ?, ?, ?);";
        var password = passwordEncryption(user.getPassword());

        try (var conn = getConnection()) {
            runner.update(conn, dataSQL, user.getId(), user.getLogin(), password, user.getStatus());
        }
    }

    @SneakyThrows
    public String getVerificationCode(Data.AuthInfo user) {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC;";

        try (var conn = getConnection()) {
            return runner.query(conn, codeSQL, new ScalarHandler<>(), user.getId());
        }
    }

    @SneakyThrows
    public void deleteDataFromDb() {
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            runner.update(conn, "DELETE FROM card_transactions");
            runner.update(conn, "DELETE FROM auth_codes");
            runner.update(conn, "DELETE FROM cards");
            runner.update(conn, "DELETE FROM users");
        }
    }
}