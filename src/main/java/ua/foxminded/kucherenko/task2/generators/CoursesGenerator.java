package ua.foxminded.kucherenko.task2.generators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.CourseDao;
import ua.foxminded.kucherenko.task2.models.Course;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CoursesGenerator implements IGenerator {
    @Autowired
    private CourseDao courseDao;

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

        for (Map.Entry<String, String> entry : courses.entrySet()) {
            courseDao.save(new Course(entry.getKey(), entry.getValue()));
        }
    }
}