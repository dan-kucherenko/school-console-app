package ua.foxminded.kucherenko.task2.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBSetup {
    private static final Dotenv DOTENV_READER = Dotenv.load();
    private static final String BASIC_URL = DOTENV_READER.get("BASIC_URL");
    private static final String DATABASE_URL = DOTENV_READER.get("DATABASE_URL");
    private static final String USER_ADMIN = DOTENV_READER.get("USER_ADMIN");
    private static final String PASSWORD_ADMIN = DOTENV_READER.get("PASSWORD_ADMIN");
    private static final String SCHOOL_USER = DOTENV_READER.get("SCHOOL_USER");
    private static final String SCHOOL_PASSWORD = DOTENV_READER.get("SCHOOL_PASSWORD");
    private static Properties PROPERTIES = getDefaultProperties();

    private static final String DB_CREATION_QUERY = """
            DROP DATABASE IF EXISTS school_db;
            CREATE DATABASE school_db;
            DROP USER IF EXISTS school_admin;
            CREATE USER school_admin WITH ENCRYPTED PASSWORD 'school_admin';
            GRANT ALL PRIVILEGES ON DATABASE school_db TO school_admin;
            """;

    private static final String TABLE_CREATION_QUERY = """
            CREATE SCHEMA school;
            DROP TABLE IF EXISTS school.groups;
            CREATE TABlE school.groups
            (
                group_id   SERIAL PRIMARY KEY,
                group_name VARCHAR(25) NOT NULL
            );
                        
            DROP TABLE IF EXISTS school.courses;
            CREATE TABLE school.courses
            (
                course_id          SERIAL PRIMARY KEY,
                course_name        VARCHAR(50)  NOT NULL,
                course_description VARCHAR(200) NOT NULL
            );
                        
            DROP TABLE IF EXISTS school.students;
            CREATE TABLE school.students
            (
                student_id SERIAL PRIMARY KEY,
                group_id   INT,
                first_name VARCHAR(25) NOT NULL,
                last_name  VARCHAR(25) NOT NULL
            );
            """;

    private DBSetup() {
    }

    private static Properties getDefaultProperties() {
        Properties props = new Properties();
        props.setProperty("user", USER_ADMIN);
        props.setProperty("password", PASSWORD_ADMIN);
        return props;
    }


    public static void initDatabase() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(BASIC_URL, PROPERTIES);
            statement = connection.prepareStatement(DB_CREATION_QUERY);
            statement.executeUpdate();

            PROPERTIES = new Properties();
            PROPERTIES.setProperty("user", SCHOOL_USER);
            PROPERTIES.setProperty("password", SCHOOL_PASSWORD);

            connection = DriverManager.getConnection(DATABASE_URL, PROPERTIES);
            statement = connection.prepareStatement(TABLE_CREATION_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
