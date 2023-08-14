package ua.foxminded.kucherenko.task2.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class DatabaseConfig implements Configuration {
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

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Properties getProps() {
        return props;
    }
}
