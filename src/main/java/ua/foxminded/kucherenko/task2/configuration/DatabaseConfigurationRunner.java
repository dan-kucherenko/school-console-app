package ua.foxminded.kucherenko.task2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;

@Configuration
public class DatabaseConfigurationRunner implements ApplicationRunner {
    @Autowired
    private CheckDbEmptiness dbEmptinessChecker;
    @Autowired
    private DataGenerator dataGenerator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dbEmptinessChecker.isStudentsTableEmpty() && dbEmptinessChecker.isCoursesTableEmpty() &&
                dbEmptinessChecker.isGroupsTableEmpty() && dbEmptinessChecker.isStudentCoursesTableEmpty()) {

            dataGenerator.generateData();
        }
    }
}
