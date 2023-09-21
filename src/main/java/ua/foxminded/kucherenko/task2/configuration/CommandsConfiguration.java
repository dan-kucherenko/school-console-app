package ua.foxminded.kucherenko.task2.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandsConfiguration.class);

    @Bean
    public Map<Integer, Runnable> commandsMap(QueryResPrinter resPrinter,
                                              StudentService studentService,
                                              StudentCoursesService studentCoursesService,
                                              GroupService groupService) {
        return Map.of(
                1, () -> {
                    LOGGER.debug("Adding new student:");
                    AddStudentInput addStudentInput = new AddStudentInput();
                    studentService.addStudent(addStudentInput.passData());
                },
                2, () -> {
                    LOGGER.debug("Adding student to course:");
                    AddStudentToCourseInput addStudentToCourseInput = new AddStudentToCourseInput();
                    studentCoursesService.addStudentToCourse(addStudentToCourseInput.passData());
                },
                3, () -> {
                    LOGGER.debug("Deleting student:");
                    DeleteStudentInput deleteStudentInput = new DeleteStudentInput();
                    studentService.deleteStudent(deleteStudentInput.passData());
                },
                4, () -> {
                    LOGGER.debug("Looking for groups with particular students number:");
                    FindGroupsStudentNumInput findGroupsStudentNumInput = new FindGroupsStudentNumInput();
                    LOGGER.info(resPrinter.printResults(groupService.findGroupsByStudNum(findGroupsStudentNumInput.passData())));
                },
                5, () -> {
                    LOGGER.debug("Looking for students by exact course:");
                    FindStudentByCourseInput findStudentByCourseInput = new FindStudentByCourseInput();
                    LOGGER.info(resPrinter.printResults(studentService.findStudentsByCourse(findStudentByCourseInput.passData())));
                },
                6, () -> {
                    LOGGER.debug("Removing a student from the course:");
                    RemoveFromCourseInput removeFromCourseInput = new RemoveFromCourseInput();
                    studentCoursesService.removeStudentFromCourse(removeFromCourseInput.passData());
                }
        );
    }
}
