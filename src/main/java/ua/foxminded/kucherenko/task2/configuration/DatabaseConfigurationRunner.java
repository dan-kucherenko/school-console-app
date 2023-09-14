package ua.foxminded.kucherenko.task2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;

@Component
public class DatabaseConfigurationRunner implements ApplicationRunner {
    @Autowired
    private CheckDbEmptiness dbEmptinessChecker;
    @Autowired
    private DataGenerator dataGenerator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dbIsEmpty()) {
            dataGenerator.generateData();
        }
    }

    private boolean dbIsEmpty() {
        return dbEmptinessChecker.isStudentsTableEmpty() && dbEmptinessChecker.isCoursesTableEmpty() &&
                dbEmptinessChecker.isGroupsTableEmpty() && dbEmptinessChecker.isStudentCoursesTableEmpty();
    }
}
