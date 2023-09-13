package ua.foxminded.kucherenko.task2.services.service_utils.find_students_num;

import ua.foxminded.kucherenko.task2.services.service_utils.IInputParser;

import java.util.Scanner;

public class FindGroupsStudentNumInput implements IInputParser<FindGroupsStudentsNumData> {
    @Override
    public FindGroupsStudentsNumData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the maximum number of students in group you want to find: ");
        int studentsQuantity = sc.nextInt();
        return new FindGroupsStudentsNumData(studentsQuantity);
    }
}
