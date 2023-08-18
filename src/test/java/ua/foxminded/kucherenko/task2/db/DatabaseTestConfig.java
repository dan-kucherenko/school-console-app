package ua.foxminded.kucherenko.task2.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class DatabaseTestConfig implements Configuration {
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private Properties PROPS;

    public DatabaseTestConfig() {
        Dotenv dotenv = Dotenv.load();

        URL = dotenv.get("DATABASE_URL");
        USERNAME = dotenv.get("SCHOOL_TEST_USER");
        PASSWORD = dotenv.get("SCHOOL_TEST_PASSWORD");
        PROPS = new Properties();
        PROPS.setProperty("user", USERNAME);
        PROPS.setProperty("password", PASSWORD);
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public Properties getProps() {
        return PROPS;
    }
}
