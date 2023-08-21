package ua.foxminded.kucherenko.task2.queries.find_stud_by_course;

import ua.foxminded.kucherenko.task2.queries.IQueryData;

public class FindStudentByCourseData implements IQueryData {
    private final String courseName;

    public FindStudentByCourseData(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
}
