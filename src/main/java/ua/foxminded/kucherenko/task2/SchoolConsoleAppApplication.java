package ua.foxminded.kucherenko.task2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class SchoolConsoleAppApplication {
    @Autowired
    private Map<Integer, Runnable> commandsMap;

    @PostConstruct
    @Profile("production")
    public void startConsole() {
        Scanner sc = new Scanner(System.in);
        int queryIndex;
        while (true) {
            System.out.println("""
                                        
                    Pick a query to run:
                    1: add new student,
                    2: add new course to the student,
                    3: delete student by id,
                    4: find groups where num of student is less or equal than given,
                    5: find students by exact course
                    6: remove student from particular course
                    -1: exit the application
                    """);
            queryIndex = sc.nextInt();
            try {
                if (queryIndex == -1) {
                    break;
                }
                commandsMap.get(queryIndex).run();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolConsoleAppApplication.class, args);
    }
}
