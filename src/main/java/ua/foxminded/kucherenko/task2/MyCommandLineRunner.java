package ua.foxminded.kucherenko.task2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
@Profile("!test")
public class MyCommandLineRunner implements CommandLineRunner {

    private final Map<Integer, Runnable> commandsMap;

    @Autowired
    public MyCommandLineRunner(Map<Integer, Runnable> commandsMap) {
        this.commandsMap = commandsMap;
    }

    @Override
    public void run(String... args) throws Exception {
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
}
