package ua.foxminded.kucherenko.task2.queries.delete_student;

import ua.foxminded.kucherenko.task2.queries.IInputParser;
import ua.foxminded.kucherenko.task2.queries.StudentExistByIdQuery;

import java.util.Scanner;

public class DeleteStudentInput implements IInputParser<DeleteStudentData> {
    @Override
    public DeleteStudentData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the studentID to delete: ");
        int studentId = sc.nextInt();
        StudentExistByIdQuery studentExistence = new StudentExistByIdQuery(studentId);
        if (studentId <= 0 || !studentExistence.executeQueryWithRes()) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }
        return new DeleteStudentData(studentId);
    }
}
