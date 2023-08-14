package ua.foxminded.kucherenko.task2.generators;

import ua.foxminded.kucherenko.task2.db.Configuration;

import java.util.List;

public class DataGenerator {
    public void generateData(Configuration configuration) {
        List<IGenerator> generators = List.of(
                new CourseNameGenerator(configuration),
                new GroupsNameGenerator(configuration),
                new StudentsGenerator(configuration),
                new StudentCourseGenerator(configuration)
        );

        for (IGenerator generator : generators) {
            generator.addToDb();
        }
        System.out.println("Initialisation was made");
    }
}
