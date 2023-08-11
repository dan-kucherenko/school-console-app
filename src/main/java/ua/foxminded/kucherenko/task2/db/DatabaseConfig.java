package ua.foxminded.kucherenko.task2.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class DatabaseConfig {
    private static String url;
    private static String username;
    private static String password;
    private static Properties props;

    public DatabaseConfig() {
        Dotenv dotenv = Dotenv.load();

        url = dotenv.get("DATABASE_URL");
        username = dotenv.get("SCHOOL_USER");
        password = dotenv.get("SCHOOL_PASSWORD");
        props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static Properties getProps() {
        return props;
    }
}
