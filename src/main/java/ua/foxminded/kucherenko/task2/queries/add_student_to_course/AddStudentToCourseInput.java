package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import ua.foxminded.kucherenko.task2.queries.IInputParser;
import ua.foxminded.kucherenko.task2.queries.StudentExistByIdQuery;
import ua.foxminded.kucherenko.task2.queries.StudentExistByNameQuery;

import java.util.Scanner;

public class AddStudentToCourseInput implements IInputParser<AddStudentToCourseData> {
    @Override
    public AddStudentToCourseData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student first name: ");
        String firstName = sc.next();
        System.out.print("Enter the student last name: ");
        String lastName = sc.next();
        StudentExistByNameQuery studentExistence = new StudentExistByNameQuery(firstName, lastName);
        int studentId = studentExistence.executeQueryWithRes();
        if (studentId == -1) {
            throw new IllegalArgumentException("Invalid student id: student id is less than 0 or student doesnt exist");
        }
        System.out.print("Enter the courseID: ");
        int courseId = sc.nextInt();
        if (courseId <= 0 || courseId > 10) {
            throw new IllegalArgumentException("Invalid ");
        }
        return new AddStudentToCourseData(studentId, courseId);
    }
}
