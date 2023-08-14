package ua.foxminded.kucherenko.task2.generators;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;

import java.util.List;

public class DataGenerator {
    public void generateData() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        List<IGenerator> generators = List.of(
                new CourseNameGenerator(databaseConfig),
                new GroupsNameGenerator(databaseConfig),
                new StudentsGenerator(databaseConfig),
                new StudentCourseGenerator(databaseConfig)
        );

        for (IGenerator generator : generators) {
            generator.addToDb();
        }
        System.out.println("Initialisation was made");
    }
}
