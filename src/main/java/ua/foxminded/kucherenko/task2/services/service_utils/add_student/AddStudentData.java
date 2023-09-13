package ua.foxminded.kucherenko.task2.services.service_utils.add_student;

import ua.foxminded.kucherenko.task2.services.service_utils.IQueryData;

public class AddStudentData implements IQueryData {
    private final int  groupId;
    private final String firstName;
    private final String lastName;

    public AddStudentData(int groupId, String firstName, String lastName) {
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
