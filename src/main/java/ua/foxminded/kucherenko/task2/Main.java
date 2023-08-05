package ua.foxminded.kucherenko.task2;

import ua.foxminded.kucherenko.task2.db.DBSetup;
import ua.foxminded.kucherenko.task2.generators.*;
import ua.foxminded.kucherenko.task2.queries.*;

import java.util.Map;
import java.util.Scanner;

public class Main {
    static {
        DBSetup.initDatabase();
    }

    public static void main(String[] args) {
        AddStudent addStudent = new AddStudent();
        AddStudentToCourse addStudentToCourse = new AddStudentToCourse();
        DeleteStudent deleteStudent = new DeleteStudent();
        FindGroupsStudentsNum findGroupsStudentsNum = new FindGroupsStudentsNum();
        FindStudentByCourse findStudentByCourse = new FindStudentByCourse();
        RemoveFromCourse removeFromCourse = new RemoveFromCourse();
        QueryResPrinter printer = new QueryResPrinter();
        Map<Integer, Runnable> map = Map.of(
                1, () -> addStudent.executeOwnQuery(),
                2, () -> addStudentToCourse.executeOwnQuery(),
                3, () -> deleteStudent.executeOwnQuery(),
                4, () -> System.out.println(printer.printResults(findGroupsStudentsNum.executeQueryWithRes())),
                5, () -> System.out.println(printer.printResults(findStudentByCourse.executeQueryWithRes())),
                6, () -> removeFromCourse.executeOwnQuery()
        );
        DataGenerator generator = new DataGenerator();
        generator.generateData();
        Scanner sc = new Scanner(System.in);
        int queryIndex;
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
        while ((queryIndex = sc.nextInt()) != -1) {
            try {
                map.get(queryIndex).run();
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
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
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
            }
        }
    }
}
