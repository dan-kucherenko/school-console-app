package ua.foxminded.kucherenko.task2.generators;

public class DataGenerator {
    public void generateData() {
        IGenerator generator;
        generator = new CourseNameGenerator();
        generator.addToDb();
        generator = new GroupsNameGenerator();
        generator.addToDb();
        generator = new StudentsGenerator();
        generator.addToDb();
        generator = new StudentCourseGenerator();
        generator.addToDb();
        System.out.println("Initialisation was made");
    }
}
