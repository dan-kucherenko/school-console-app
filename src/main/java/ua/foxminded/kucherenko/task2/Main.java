package ua.foxminded.kucherenko.task2;

import ua.foxminded.kucherenko.task2.db.DatabaseCreator;
import ua.foxminded.kucherenko.task2.db.TablesCreator;
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
        DatabaseCreator databaseCreator = new DatabaseCreator();
        databaseCreator.initDatabase();
        TablesCreator tablesCreator = new TablesCreator();
        tablesCreator.createTables();
        DataGenerator generator = new DataGenerator();
        DatabaseConfig databaseConfig = new DatabaseConfig();
        generator.generateData(databaseConfig);
    }

    private static final DatabaseConfig databaseConfig = new DatabaseConfig();
    private static final AddStudent addStudent = new AddStudent(databaseConfig);
    private static final AddStudentToCourse addStudentToCourse = new AddStudentToCourse(databaseConfig);
    private static final DeleteStudent deleteStudent = new DeleteStudent(databaseConfig);
    private static final FindGroupsStudentsNum findGroupsStudentsNum = new FindGroupsStudentsNum(databaseConfig);
    private static final FindStudentByCourse findStudentByCourse = new FindStudentByCourse(databaseConfig);
    private static final RemoveFromCourse removeFromCourse = new RemoveFromCourse(databaseConfig);
    private static final QueryResPrinter printer = new QueryResPrinter();

    private static final Map<Integer, Runnable> map = Map.of(
            1, () -> {
                AddStudentInput addStudentInput = new AddStudentInput();
                addStudent.executeQuery(addStudentInput.passData());
            },
            2, () -> {
                AddStudentToCourseInput addStudentToCourseInput = new AddStudentToCourseInput(databaseConfig);
                addStudentToCourse.executeQuery(addStudentToCourseInput.passData());
            },
            3, () -> {
                DeleteStudentInput deleteStudentInput = new DeleteStudentInput();
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int queryIndex = 0;
        while (true){
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
                if (queryIndex == -1) {
                    break;
                }
                map.get(queryIndex).run();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
