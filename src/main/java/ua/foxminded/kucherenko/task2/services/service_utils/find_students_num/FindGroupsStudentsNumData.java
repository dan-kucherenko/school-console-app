package ua.foxminded.kucherenko.task2.services.service_utils.find_students_num;

import ua.foxminded.kucherenko.task2.services.service_utils.IQueryData;

public class FindGroupsStudentsNumData implements IQueryData {
    private final int studentsQuantity;

    public FindGroupsStudentsNumData(int studentsQuantity) {
        this.studentsQuantity = studentsQuantity;
    }

    public int getStudentsQuantity() {
        return studentsQuantity;
    }
}
