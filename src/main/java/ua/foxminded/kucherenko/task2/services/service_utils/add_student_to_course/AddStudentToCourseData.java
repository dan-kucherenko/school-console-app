package ua.foxminded.kucherenko.task2.services.service_utils.add_student_to_course;

import ua.foxminded.kucherenko.task2.services.service_utils.IQueryData;

public class AddStudentToCourseData implements IQueryData {
    private final String firstName;
    private final String lastName;
    private final int courseId;

    public AddStudentToCourseData(String firstName, String lastName, int courseId) {
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
