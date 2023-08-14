package ua.foxminded.kucherenko.task2.data_generator;

import ua.foxminded.kucherenko.task2.db.DatabaseTestConfig;
import ua.foxminded.kucherenko.task2.generators.CourseNameGenerator;
import ua.foxminded.kucherenko.task2.generators.GroupsNameGenerator;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourse;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourseData;

public class AddDataForTest {
    private final DatabaseTestConfig testConfig = new DatabaseTestConfig();
    private final AddStudent addStudent = new AddStudent(testConfig);
    private final AddStudentToCourse addStudentToCourse = new AddStudentToCourse(testConfig);

    public void addStudents() {
        GroupsNameGenerator groupGenerator = new GroupsNameGenerator(testConfig);
        groupGenerator.addToDb();
        AddStudentData data;

        data = new AddStudentData(4, "John", "Johnson");
        addStudent.executeQuery(data);

        data = new AddStudentData(4, "Michael", "Michaelson");
        addStudent.executeQuery(data);

        data = new AddStudentData(4, "Emma", "Emmson");
        addStudent.executeQuery(data);

        data = new AddStudentData(4, "Alice", "Alison");
        addStudent.executeQuery(data);

        data = new AddStudentData(5, "David", "Davidson");
        addStudent.executeQuery(data);

        data = new AddStudentData(5, "John1", "Johnson1");
        addStudent.executeQuery(data);

        data = new AddStudentData(5, "Michael1", "Michaelson1");
        addStudent.executeQuery(data);

        data = new AddStudentData(5, "Emma1", "Emmson1");
        addStudent.executeQuery(data);

        data = new AddStudentData(6, "Alice1", "Alison1");
        addStudent.executeQuery(data);

        data = new AddStudentData(6, "David1", "Davidson1");
        addStudent.executeQuery(data);

        data = new AddStudentData(5, "John2", "Johnson2");
        addStudent.executeQuery(data);

        data = new AddStudentData(5, "Michael2", "Michaelson2");
        addStudent.executeQuery(data);

        data = new AddStudentData(5, "Emma2", "Emmson2");
        addStudent.executeQuery(data);

        data = new AddStudentData(6, "Alice2", "Alison2");
        addStudent.executeQuery(data);

        data = new AddStudentData(6, "David2", "Davidson2");
        addStudent.executeQuery(data);
    }

    public void addStudentCourses() {
        AddStudentToCourseData data;

        CourseNameGenerator courseGenerator = new CourseNameGenerator(testConfig);
        courseGenerator.addToDb();

        data = new AddStudentToCourseData(1, 5);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(2, 5);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(3, 5);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(4, 3);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(4, 5);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(5, 6);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(6, 5);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(7, 5);
        addStudentToCourse.executeQuery(data);

        data = new AddStudentToCourseData(8, 5);
        addStudentToCourse.executeQuery(data);
    }
}
