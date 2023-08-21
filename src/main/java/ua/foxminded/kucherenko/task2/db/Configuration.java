package ua.foxminded.kucherenko.task2.db;

import java.util.Properties;

public interface Configuration {
    String getUrl();
    String getUsername();
    String getPassword();
    Properties getProps();
}
