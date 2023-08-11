package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import ua.foxminded.kucherenko.task2.queries.IQueryData;

public class RemoveFromCourseData implements IQueryData {
    private final int studentId;
    private final int courseId;

    public RemoveFromCourseData(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }
}
