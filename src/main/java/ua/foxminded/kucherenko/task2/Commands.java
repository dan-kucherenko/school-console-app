package ua.foxminded.kucherenko.task2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
public class Commands {
    @Autowired
    private QueryResPrinter resPrinter;
    @Autowired
    private AddStudent addStudent;
    @Autowired
    private AddStudentToCourse addStudentToCourse;
    @Autowired
    private DeleteStudent deleteStudent;
    @Autowired
    private FindGroupsStudentsNum findGroupsStudentsNum;
    @Autowired
    private FindStudentByCourse findStudentByCourse;
    @Autowired
    private RemoveFromCourse removeFromCourse;

    @Bean
    public Map<Integer, Runnable> commandsMap() {
        return Map.of(
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
                    System.out.println(resPrinter.printResults(findGroupsStudentsNum.executeQueryWithRes(findGroupsStudentNumInput.passData())));
                },
                5, () -> {
                    FindStudentByCourseInput findStudentByCourseInput = new FindStudentByCourseInput();
                    System.out.println(resPrinter.printResults(findStudentByCourse.executeQueryWithRes(findStudentByCourseInput.passData())));
                },
                6, () -> {
                    RemoveFromCourseInput removeFromCourseInput = new RemoveFromCourseInput();
                    removeFromCourse.executeQuery(removeFromCourseInput.passData());
                }
        );
    }
}
