package ua.foxminded.kucherenko.task2;

import ua.foxminded.kucherenko.task2.db.CreateDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTables;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.generators.*;
import ua.foxminded.kucherenko.task2.queries.*;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentInput;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourse;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourseInput;
import ua.foxminded.kucherenko.task2.queries.delete_student.DeleteStudent;
import ua.foxminded.kucherenko.task2.queries.delete_student.DeleteStudentInput;
import ua.foxminded.kucherenko.task2.queries.find_stud_by_course.FindStudentByCourse;
import ua.foxminded.kucherenko.task2.queries.find_stud_by_course.FindStudentByCourseInput;
import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentNumInput;
import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentsNum;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourse;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourseInput;

import java.util.Map;
import java.util.Scanner;

public class Main {
    static {
        CreateDatabase.initDatabase();
        CreateTables.createTables();
        DataGenerator generator = new DataGenerator();
        generator.generateData();
    }

    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        AddStudent addStudent = new AddStudent(databaseConfig);
        AddStudentToCourse addStudentToCourse = new AddStudentToCourse(databaseConfig);
        DeleteStudent deleteStudent = new DeleteStudent(databaseConfig);
        FindGroupsStudentsNum findGroupsStudentsNum = new FindGroupsStudentsNum(databaseConfig);
        FindStudentByCourse findStudentByCourse = new FindStudentByCourse(databaseConfig);
        RemoveFromCourse removeFromCourse = new RemoveFromCourse(databaseConfig);
        QueryResPrinter printer = new QueryResPrinter();
        Map<Integer, Runnable> map = Map.of(
                1, () -> {
                    AddStudentInput addStudentInput = new AddStudentInput(databaseConfig);
                    addStudent.executeQuery(addStudentInput.passData());
                },
                2, () -> {
                    AddStudentToCourseInput addStudentToCourseInput = new AddStudentToCourseInput(databaseConfig);
                    addStudentToCourse.executeQuery(addStudentToCourseInput.passData());
                },
                3, () -> {
                    DeleteStudentInput deleteStudentInput = new DeleteStudentInput(databaseConfig);
                    deleteStudent.executeQuery(deleteStudentInput.passData());
                },
                4, () -> {
                    FindGroupsStudentNumInput findGroupsStudentNumInput = new FindGroupsStudentNumInput();
                    System.out.println(printer.printResults(findGroupsStudentsNum.executeQueryWithRes(findGroupsStudentNumInput.passData())));
                },
                5, () -> {
                    FindStudentByCourseInput findStudentByCourseInput = new FindStudentByCourseInput();
                    System.out.println(printer.printResults(findStudentByCourse.executeQueryWithRes(findStudentByCourseInput.passData())));
                },
                6, () -> {
                    RemoveFromCourseInput removeFromCourseInput = new RemoveFromCourseInput(databaseConfig);
                    removeFromCourse.executeQuery(removeFromCourseInput.passData());
                }
        );
        Scanner sc = new Scanner(System.in);
        int queryIndex;
        do {
            System.out.println("""
                                        
                    Pick a query to run:
                    1: add new student,
                    2: add new course to the student,
                    3: delete student by id,
                    4: find groups where num of student is less or equal than given,
                    5: find students by exact course
                    6: remove student from particular course
                    -1: exit the application
                    """);
            queryIndex = sc.nextInt();
            try {
                map.get(queryIndex).run();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        while (queryIndex != -1);
    }
}
