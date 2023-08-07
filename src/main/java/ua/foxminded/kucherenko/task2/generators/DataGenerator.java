package ua.foxminded.kucherenko.task2.generators;

import java.util.List;

public class DataGenerator {
    public void generateData() {
        List<IGenerator> generators = List.of(
                new CourseNameGenerator(),
                new GroupsNameGenerator(),
                new StudentsGenerator(),
                new StudentCourseGenerator()
        );

        for (IGenerator generator : generators) {
            generator.addToDb();
        }
        System.out.println("Initialisation was made");
    }
}
