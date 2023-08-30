package ua.foxminded.kucherenko.task2.queries.add_student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class AddStudent implements IVoidQuery<AddStudentData> {
    private final StudentDao studentDao;
    private final GroupDao groupDao;


    @Autowired
    public AddStudent(JdbcTemplate jdbcTemplate) {
        this.studentDao = new StudentDao(jdbcTemplate);
        this.groupDao = new GroupDao(jdbcTemplate);
    }

    @Override
    public void executeQuery(AddStudentData data) {
        int groupQuantity = groupDao.getGroupQuantity(data.getGroupId());

        if (data.getGroupId() < 0 || groupIsFull(groupQuantity)) {
            throw new IllegalArgumentException("GroupID should be a positive number or group is full");
        }

        try {
            Student student = new Student(data.getGroupId(), data.getFirstName(), data.getLastName());
            studentDao.save(student);
            System.out.println("Student " + data.getFirstName() + ' ' + data.getLastName() + " from group " +
                    data.getGroupId() + " was successfully added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean groupIsFull(int groupQuantity) {
        return groupQuantity >= 30;
    }
}
