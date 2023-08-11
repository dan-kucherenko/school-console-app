package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import ua.foxminded.kucherenko.task2.queries.IInputParser;
import ua.foxminded.kucherenko.task2.queries.StudentExistByNameQuery;

import java.util.Scanner;

public class RemoveFromCourseInput implements IInputParser<RemoveFromCourseData> {

    @Override
    public RemoveFromCourseData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student first name to delete: ");
        String firstName = sc.next();
        System.out.print("Enter the student last name to delete: ");
        String lastName = sc.next();
        StudentExistByNameQuery studentExistence = new StudentExistByNameQuery(firstName, lastName);
        int studentId = studentExistence.executeQueryWithRes();
        if (studentId == -1) {
            throw new IllegalArgumentException("Student doesn't exist or the studentId is incorrect");
        }
        System.out.println("Enter the courseId to delete: ");
        int courseId = sc.nextInt();
        if (courseId <= 0 || courseId > 10) {
            throw new IllegalArgumentException("Course Id should be between 1 and 10");
        }
        return new RemoveFromCourseData(studentId, courseId);
    }
}
