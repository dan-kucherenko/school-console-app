package ua.foxminded.kucherenko.task2.generators;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CourseNameGenerator implements IGenerator {
    private static Map<String, String> courses;
    private static final String QUERY = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";

    public CourseNameGenerator() {
        if (courses == null) {
            courses = getCoursesNames();
        }
    }

    @Override
    public void addToDb() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(QUERY);

            for (Map.Entry<String, String> entry : courses.entrySet()) {
                statement.setString(1, entry.getKey());
                statement.setString(2, entry.getValue());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> getCoursesNames() {
        Map<String, String> coursesData = new HashMap<>();
        coursesData.put("Math", "Study of numbers, quantities, and shapes.");
        coursesData.put("Biology", "Study of living organisms and their interactions.");
        coursesData.put("Physics", "Study of matter, energy, and fundamental forces.");
        coursesData.put("Chemistry", "Study of substances, their properties, and reactions.");
        coursesData.put("History", "Study of past events and human societies.");
        coursesData.put("English", "Study of the English language and literature.");
        coursesData.put("Computer Science", "Study of computation and information processing.");
        coursesData.put("Geography", "Study of the Earth's landscapes and environments.");
        coursesData.put("Art", "Study of visual arts and creative expression.");
        coursesData.put("Music", "Study of musical theory, composition, and performance.");
        return coursesData;
    }
}
