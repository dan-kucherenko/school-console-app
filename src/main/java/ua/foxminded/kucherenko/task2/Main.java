package ua.foxminded.kucherenko.task2;

import ua.foxminded.kucherenko.task2.db.ConfigReader;
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
    private static final ConfigReader READER = new ConfigReader();
    private static final DatabaseConfig SCHOOL_ADMIN_CONFIGURATION = READER.readSchoolAdminConfiguration();
    private static final DatabaseConfig ADMIN_CONFIG = READER.readAdminConfiguration();

    static {
        DatabaseCreator databaseCreator = new DatabaseCreator(ADMIN_CONFIG);
        databaseCreator.initDatabase();
        TablesCreator tablesCreator = new TablesCreator(SCHOOL_ADMIN_CONFIGURATION);
        tablesCreator.createTables();
        DataGenerator generator = new DataGenerator();
        generator.generateData(SCHOOL_ADMIN_CONFIGURATION);
    }

    private static final AddStudent ADD_STUDENT_OPERATION = new AddStudent(SCHOOL_ADMIN_CONFIGURATION);
    private static final AddStudentToCourse ADD_STUDENT_TO_COURSE = new AddStudentToCourse(SCHOOL_ADMIN_CONFIGURATION);
    private static final DeleteStudent DELETE_STUDENT = new DeleteStudent(SCHOOL_ADMIN_CONFIGURATION);
    private static final FindGroupsStudentsNum FIND_GROUPS_STUDENTS_NUM = new FindGroupsStudentsNum(SCHOOL_ADMIN_CONFIGURATION);
    private static final FindStudentByCourse FIND_STUDENT_BY_COURSE = new FindStudentByCourse(SCHOOL_ADMIN_CONFIGURATION);
    private static final RemoveFromCourse REMOVE_FROM_COURSE = new RemoveFromCourse(SCHOOL_ADMIN_CONFIGURATION);
    private static final QueryResPrinter RES_PRINTER = new QueryResPrinter();

    private static final Map<Integer, Runnable> map = Map.of(
            1, () -> {
                AddStudentInput addStudentInput = new AddStudentInput();
                ADD_STUDENT_OPERATION.executeQuery(addStudentInput.passData());
            },
            2, () -> {
                AddStudentToCourseInput addStudentToCourseInput = new AddStudentToCourseInput();
                ADD_STUDENT_TO_COURSE.executeQuery(addStudentToCourseInput.passData());
            },
            3, () -> {
                DeleteStudentInput deleteStudentInput = new DeleteStudentInput();
                DELETE_STUDENT.executeQuery(deleteStudentInput.passData());
            },
            4, () -> {
                FindGroupsStudentNumInput findGroupsStudentNumInput = new FindGroupsStudentNumInput();
                System.out.println(RES_PRINTER.printResults(FIND_GROUPS_STUDENTS_NUM.executeQueryWithRes(findGroupsStudentNumInput.passData())));
            },
            5, () -> {
                FindStudentByCourseInput findStudentByCourseInput = new FindStudentByCourseInput();
                System.out.println(RES_PRINTER.printResults(FIND_STUDENT_BY_COURSE.executeQueryWithRes(findStudentByCourseInput.passData())));
            },
            6, () -> {
                RemoveFromCourseInput removeFromCourseInput = new RemoveFromCourseInput();
                REMOVE_FROM_COURSE.executeQuery(removeFromCourseInput.passData());
            }
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int queryIndex;
        while (true) {
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
