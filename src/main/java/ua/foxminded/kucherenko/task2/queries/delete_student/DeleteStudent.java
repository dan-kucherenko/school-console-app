package ua.foxminded.kucherenko.task2.queries.delete_student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class DeleteStudent implements IVoidQuery<DeleteStudentData> {
    @Autowired
    private StudentDao studentDao;
    private static final Logger LOGGER = LogManager.getLogger(DeleteStudent.class);

    @Override
    public void executeQuery(DeleteStudentData data) {
        if (data.getStudentId() <= 0 || studentDao.get(data.getStudentId()).isEmpty()) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }

        studentDao.delete(data.getStudentId());
        LOGGER.info("Student with id " + data.getStudentId() + " was successfully deleted");
    }
}
