package ua.foxminded.kucherenko.task2.services.service_utils.add_student;

import ua.foxminded.kucherenko.task2.services.service_utils.IInputParser;

import java.util.Scanner;

public class AddStudentInput implements IInputParser<AddStudentData> {
    public AddStudentData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student groupID (0 if student isnt in group): ");
        int groupId = sc.nextInt();

        System.out.print("Enter the student first name: ");
        String firstName = sc.next();

        System.out.print("Enter the student last name: ");
        String lastName = sc.next();

        return new AddStudentData(groupId, firstName, lastName);
    }
}
