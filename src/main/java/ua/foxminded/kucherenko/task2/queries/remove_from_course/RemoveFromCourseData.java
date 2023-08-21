package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import ua.foxminded.kucherenko.task2.queries.IQueryData;

public class RemoveFromCourseData implements IQueryData {
    private final String firstName;
    private final String lastName;
    private final int courseId;

    public RemoveFromCourseData(String firstName, String lastName, int courseId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseId = courseId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCourseId() {
        return courseId;
    }
}
