package ua.foxminded.kucherenko.task2.queries.delete_student;

import ua.foxminded.kucherenko.task2.queries.IInputParser;

import java.util.Scanner;

public class DeleteStudentInput implements IInputParser<DeleteStudentData> {
    @Override
    public DeleteStudentData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the studentID to delete: ");
        int studentId = sc.nextInt();
        return new DeleteStudentData(studentId);
    }
}
