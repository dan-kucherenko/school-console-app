package ua.foxminded.kucherenko.task2.generators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataGenerator {
    @Autowired
    private CoursesGenerator coursesGenerator;
    @Autowired
    private GroupsGenerator groupsGenerator;
    @Autowired
    private StudentsGenerator studentsGenerator;
    @Autowired
    private StudentCourseGenerator studentCourseGenerator;
    private static final Logger LOGGER = LogManager.getLogger(DataGenerator.class);
//    private static final String REFRESH_ID_QUERY_FILEPATH = "src/main/resources/sql_queries/database/refresh_autoincrement.sql";

//    private static final String REFRESH_ID = QueryParser.parseQuery(REFRESH_ID_QUERY_FILEPATH);

    public void generateData() {
        List<IGenerator> generators = List.of(
                coursesGenerator,
                groupsGenerator,
                studentsGenerator,
                studentCourseGenerator
        );
        generators.forEach(IGenerator::addToDb);
        LOGGER.info("Initialisation was made");
    }
}