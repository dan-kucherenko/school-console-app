package ua.foxminded.kucherenko.task2.queries;

import java.util.Properties;

public interface IQuery {
    String URL = "jdbc:postgresql://localhost:5432/school_db";
    String USER = "school_admin";
    String PASSWORD = "school_admin";
    Properties PROPERTIES = getDefaultProperties();

    static Properties getDefaultProperties() {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        return props;
    }
}
