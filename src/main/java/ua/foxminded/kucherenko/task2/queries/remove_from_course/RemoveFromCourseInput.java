package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import ua.foxminded.kucherenko.task2.queries.IInputParser;

import java.util.Scanner;

public class RemoveFromCourseInput implements IInputParser<RemoveFromCourseData> {
    @Override
    public RemoveFromCourseData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student first name to delete: ");
        String firstName = sc.next();
        System.out.print("Enter the student last name to delete: ");
        String lastName = sc.next();

        System.out.print("Enter the courseId to delete: ");
        int courseId = sc.nextInt();

        return new RemoveFromCourseData(firstName, lastName, courseId);
    }
}
