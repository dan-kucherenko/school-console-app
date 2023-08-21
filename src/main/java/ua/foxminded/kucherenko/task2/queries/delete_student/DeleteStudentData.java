package ua.foxminded.kucherenko.task2.queries.delete_student;

import ua.foxminded.kucherenko.task2.queries.IQueryData;

public class DeleteStudentData implements IQueryData {
    private final int studentId;

    public DeleteStudentData(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }
}
