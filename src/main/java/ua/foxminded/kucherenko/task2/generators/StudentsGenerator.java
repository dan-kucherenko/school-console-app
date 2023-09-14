package ua.foxminded.kucherenko.task2.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.services.StudentService;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student.AddStudentData;

import java.util.*;

@Component
public class StudentsGenerator implements IGenerator {
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private StudentService studentService;
    private static final int NUMBER_OF_STUDENTS = 200;
    private static final int MAX_GROUP_CAPACITY = 30;
    private static final int NO_GROUP_INDEX = 0;

    private static final String[] FIRST_NAMES = {
            "John", "Emma", "Michael", "Sophia", "William",
            "Olivia", "James", "Ava", "Alexander", "Isabella",
            "Daniel", "Mia", "Joseph", "Amelia", "David",
            "Charlotte", "Andrew", "Ella", "Anthony", "Emily"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Lee", "Davis",
            "Garcia", "Martinez", "Miller", "Wilson", "Anderson",
            "Taylor", "Thomas", "Jackson", "White", "Harris",
            "Martin", "Lewis", "Allen", "Young", "Clark"
    };

    @Override
    public void addToDb() {
        Random random = new Random();

        List<Integer> availableGroupIds = groupDao.getAllGroupIds();

        Set<Integer> assignedStudents = new HashSet<>();
        Map<Integer, Integer> groupCounts = new HashMap<>();

        for (int studentId = 1; studentId <= NUMBER_OF_STUDENTS; studentId++) {
            int groupId = random.nextInt(availableGroupIds.size() + 1);

            while ((groupId != NO_GROUP_INDEX) &&
                    (groupCounts.getOrDefault(groupId, 0) == MAX_GROUP_CAPACITY
                            || assignedStudents.contains(studentId))) {
                groupId = random.nextInt(availableGroupIds.size() + 1);
            }

            boolean studentShouldBeAddedToGroup = random.nextBoolean();
            if (groupId != NO_GROUP_INDEX && studentShouldBeAddedToGroup) {
                groupCounts.put(groupId, groupCounts.getOrDefault(groupId, 0) + 1);
                assignedStudents.add(studentId);
            }
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

            studentService.addStudent(new AddStudentData(groupId, firstName, lastName));
        }
    }
}