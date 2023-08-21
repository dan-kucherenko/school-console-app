package ua.foxminded.kucherenko.task2.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class TestConfigReader {
    public DatabaseConfig readAdminConfiguration(){
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("BASIC_URL");
        String username = dotenv.get("USER_ADMIN");
        String password = dotenv.get("PASSWORD_ADMIN");
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        return new DatabaseConfig(url, username, password, properties);
    }

    public DatabaseConfig readTestSchoolAdminConfiguration(){
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DATABASE_URL");
        String username = dotenv.get("SCHOOL_TEST_USER");
        String password = dotenv.get("SCHOOL_TEST_PASSWORD");
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        return new DatabaseConfig(url, username, password, properties);
    }
}
