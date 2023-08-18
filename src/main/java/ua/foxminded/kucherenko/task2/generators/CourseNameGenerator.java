package ua.foxminded.kucherenko.task2.generators;


import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class CourseNameGenerator implements IGenerator {
    private static final int COURSE_NAME_INDEX = 1;
    private static final int COURSE_DESCRIPTION_INDEX = 2;
    private static final String ADD_COURSE_QUERY_FILEPATH = "src/main/resources/sql_queries/generators/insert_course.sql";
    private static final String ADD_COURSE_QUERY = QueryParser.parseQuery(ADD_COURSE_QUERY_FILEPATH);
    private final String url;
    private final Properties properties;

    public CourseNameGenerator(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public void addToDb() {
        Map<String, String> courses = new LinkedHashMap<>();
        courses.put("Math", "Study of numbers, quantities, and shapes.");
        courses.put("Biology", "Study of living organisms and their interactions.");
        courses.put("Physics", "Study of matter, energy, and fundamental forces.");
        courses.put("Chemistry", "Study of substances, their properties, and reactions.");
        courses.put("History", "Study of past events and human societies.");
        courses.put("English", "Study of the English language and literature.");
        courses.put("Computer Science", "Study of computation and information processing.");
        courses.put("Geography", "Study of the Earth's landscapes and environments.");
        courses.put("Art", "Study of visual arts and creative expression.");
        courses.put("Music", "Study of musical theory, composition, and performance.");
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(ADD_COURSE_QUERY)) {

            for (Map.Entry<String, String> entry : courses.entrySet()) {
                statement.setString(COURSE_NAME_INDEX, entry.getKey());
                statement.setString(COURSE_DESCRIPTION_INDEX, entry.getValue());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
