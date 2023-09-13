package ua.foxminded.kucherenko.task2.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.foxminded.kucherenko.task2.services.service_utils.QueryResPrinter;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student.AddStudentInput;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student_to_course.AddStudentToCourseInput;
import ua.foxminded.kucherenko.task2.services.service_utils.delete_student.DeleteStudentInput;
import ua.foxminded.kucherenko.task2.services.service_utils.find_stud_by_course.FindStudentByCourseInput;
import ua.foxminded.kucherenko.task2.services.service_utils.find_students_num.FindGroupsStudentNumInput;
import ua.foxminded.kucherenko.task2.services.service_utils.remove_from_course.RemoveFromCourseInput;
import ua.foxminded.kucherenko.task2.services.GroupService;
import ua.foxminded.kucherenko.task2.services.StudentCoursesService;
import ua.foxminded.kucherenko.task2.services.StudentService;

import java.util.Map;

@Configuration
public class CommandsConfiguration {
    private static final Logger LOGGER = LogManager.getLogger(CommandsConfiguration.class);

    @Bean
    public Map<Integer, Runnable> commandsMap(@Autowired QueryResPrinter resPrinter,
                                              @Autowired StudentService studentService,
                                              @Autowired StudentCoursesService studentCoursesService,
                                              @Autowired GroupService groupService) {
        return Map.of(
                1, () -> {
                    AddStudentInput addStudentInput = new AddStudentInput();
                    studentService.addStudent(addStudentInput.passData());
                },
                2, () -> {
                    AddStudentToCourseInput addStudentToCourseInput = new AddStudentToCourseInput();
                    studentCoursesService.addStudentToCourse(addStudentToCourseInput.passData());
                },
                3, () -> {
                    DeleteStudentInput deleteStudentInput = new DeleteStudentInput();
                    studentService.deleteStudent(deleteStudentInput.passData());
                },
                4, () -> {
                    FindGroupsStudentNumInput findGroupsStudentNumInput = new FindGroupsStudentNumInput();
                    LOGGER.info(resPrinter.printResults(groupService.findGroupsByStudNum(findGroupsStudentNumInput.passData())));
                },
                5, () -> {
                    FindStudentByCourseInput findStudentByCourseInput = new FindStudentByCourseInput();
                    LOGGER.info(resPrinter.printResults(studentService.findStudentsByCourse(findStudentByCourseInput.passData())));
                },
                6, () -> {
                    RemoveFromCourseInput removeFromCourseInput = new RemoveFromCourseInput();
                    studentCoursesService.removeStudentFromCourse(removeFromCourseInput.passData());
                }
        );
    }
}
