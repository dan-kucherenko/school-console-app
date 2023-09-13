package ua.foxminded.kucherenko.task2.services.service_utils.delete_student;

import ua.foxminded.kucherenko.task2.services.service_utils.IQueryData;

public class DeleteStudentData implements IQueryData {
    private final int studentId;

    public DeleteStudentData(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }
}
