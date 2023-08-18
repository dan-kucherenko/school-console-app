package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import ua.foxminded.kucherenko.task2.queries.IQueryData;

public class AddStudentToCourseData implements IQueryData {
    private final Integer studentId;
    private final int courseId;

    public AddStudentToCourseData(Integer studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }
}
