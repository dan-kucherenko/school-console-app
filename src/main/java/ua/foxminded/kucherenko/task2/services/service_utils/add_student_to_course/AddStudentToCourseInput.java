package ua.foxminded.kucherenko.task2.services.service_utils.add_student_to_course;

import ua.foxminded.kucherenko.task2.services.service_utils.IInputParser;

import java.util.Scanner;

public class AddStudentToCourseInput implements IInputParser<AddStudentToCourseData> {
    @Override
    public AddStudentToCourseData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student first name: ");
        String firstName = sc.next();
        System.out.print("Enter the student last name: ");
        String lastName = sc.next();

        System.out.print("Enter the courseID: ");
        int courseId = sc.nextInt();

        return new AddStudentToCourseData(firstName, lastName, courseId);
    }
}
