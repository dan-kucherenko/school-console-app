package ua.foxminded.kucherenko.task2.queries.add_student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class AddStudent implements IVoidQuery<AddStudentData> {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private GroupDao groupDao;
    private static final int MAX_GROUP_CAPACITY = 30;
    private static final Logger LOGGER = LogManager.getLogger(AddStudent.class);

    @Override
    public void executeQuery(AddStudentData data) {
        if (data.getGroupId() < 0) {
            throw new IllegalArgumentException("GroupID should be a positive number");
        }

        int groupQuantity = groupDao.getGroupQuantity(data.getGroupId());

        if (groupIsFull(groupQuantity)) {
            throw new IllegalArgumentException("Group is full");
        }

        try {
            Student student = new Student(data.getGroupId(), data.getFirstName(), data.getLastName());
            studentDao.save(student);
            LOGGER.info("Student " + data.getFirstName() + ' ' + data.getLastName() + " from group " +
                    data.getGroupId() + " was successfully added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean groupIsFull(int groupQuantity) {
        return groupQuantity >= MAX_GROUP_CAPACITY;
    }
}
