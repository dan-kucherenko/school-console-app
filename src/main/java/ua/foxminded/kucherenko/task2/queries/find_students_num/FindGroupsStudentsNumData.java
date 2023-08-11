package ua.foxminded.kucherenko.task2.queries.find_students_num;

import ua.foxminded.kucherenko.task2.queries.IQueryData;

public class FindGroupsStudentsNumData implements IQueryData {
    private final int studentsQuantity;

    public FindGroupsStudentsNumData(int studentsQuantity) {
        this.studentsQuantity = studentsQuantity;
    }

    public int getStudentsQuantity() {
        return studentsQuantity;
    }
}
