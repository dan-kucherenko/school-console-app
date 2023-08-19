package ua.foxminded.kucherenko.task2.db;

import java.util.Properties;

public class DatabaseConfig implements Configuration {
    private final String url;
    private final String username;
    private final String password;
    private final Properties props;

    public DatabaseConfig(String url, String username, String password, Properties props) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.props = props;
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
