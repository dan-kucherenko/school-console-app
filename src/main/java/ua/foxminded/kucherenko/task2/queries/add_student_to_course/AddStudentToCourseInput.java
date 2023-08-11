package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import ua.foxminded.kucherenko.task2.queries.IInputParser;
import ua.foxminded.kucherenko.task2.queries.StudentExistByIdQuery;

import java.util.Scanner;

public class AddStudentToCourseInput implements IInputParser<AddStudentToCourseData> {
    @Override
    public AddStudentToCourseData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the studentID: ");
        int studentId = sc.nextInt();
        StudentExistByIdQuery studentExistence = new StudentExistByIdQuery(studentId);
        if (studentId <= 0 || !studentExistence.executeQueryWithRes()) {
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
