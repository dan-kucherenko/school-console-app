package ua.foxminded.kucherenko.task2.services.service_utils.find_stud_by_course;

import ua.foxminded.kucherenko.task2.services.service_utils.IInputParser;

import java.util.Scanner;

public class FindStudentByCourseInput implements IInputParser<FindStudentByCourseData> {
    @Override
    public FindStudentByCourseData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the course for students you want to find: ");
        String courseName = sc.next();
        return new FindStudentByCourseData(courseName);
    }
}
