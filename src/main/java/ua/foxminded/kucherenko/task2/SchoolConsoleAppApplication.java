package ua.foxminded.kucherenko.task2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ua.foxminded.kucherenko.task2.queries.QueryResPrinter;
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

@SpringBootApplication
public class SchoolConsoleAppApplication {
    private final Map<Integer, Runnable> map;
    private static final QueryResPrinter RES_PRINTER = new QueryResPrinter();

    @Autowired
    public SchoolConsoleAppApplication(
            AddStudent addStudent,
            AddStudentToCourse addStudentToCourse,
            DeleteStudent deleteStudent,
            FindGroupsStudentsNum findGroupsStudentsNum,
            FindStudentByCourse findStudentByCourse,
            RemoveFromCourse removeFromCourse
    ) {
        map = Map.of(
                1, () -> {
                    AddStudentInput addStudentInput = new AddStudentInput();
                    addStudent.executeQuery(addStudentInput.passData());
                },
                2, () -> {
                    AddStudentToCourseInput addStudentToCourseInput = new AddStudentToCourseInput();
                    addStudentToCourse.executeQuery(addStudentToCourseInput.passData());
                },
                3, () -> {
                    DeleteStudentInput deleteStudentInput = new DeleteStudentInput();
                    deleteStudent.executeQuery(deleteStudentInput.passData());
                },
                4, () -> {
                    FindGroupsStudentNumInput findGroupsStudentNumInput = new FindGroupsStudentNumInput();
                    System.out.println(RES_PRINTER.printResults(findGroupsStudentsNum.executeQueryWithRes(findGroupsStudentNumInput.passData())));
                },
                5, () -> {
                    FindStudentByCourseInput findStudentByCourseInput = new FindStudentByCourseInput();
                    System.out.println(RES_PRINTER.printResults(findStudentByCourse.executeQueryWithRes(findStudentByCourseInput.passData())));
                },
                6, () -> {
                    RemoveFromCourseInput removeFromCourseInput = new RemoveFromCourseInput();
                    removeFromCourse.executeQuery(removeFromCourseInput.passData());
                }
        );
    }


    public void startConsole() {
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

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SchoolConsoleAppApplication.class, args);
        SchoolConsoleAppApplication app = context.getBean(SchoolConsoleAppApplication.class);
        app.startConsole();
        context.close();
    }
}
