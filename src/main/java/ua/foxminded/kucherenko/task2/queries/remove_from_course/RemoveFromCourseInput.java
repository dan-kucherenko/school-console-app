package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import ua.foxminded.kucherenko.task2.queries.IInputParser;
import ua.foxminded.kucherenko.task2.queries.StudentExistByIdQuery;

import java.util.Scanner;

public class RemoveFromCourseInput implements IInputParser<RemoveFromCourseData> {

    @Override
    public RemoveFromCourseData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the studentId to delete: ");
        int studentId = sc.nextInt();
        StudentExistByIdQuery studentExistence = new StudentExistByIdQuery(studentId);
        if (studentId <= 0 || !studentExistence.executeQueryWithRes()) {
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
