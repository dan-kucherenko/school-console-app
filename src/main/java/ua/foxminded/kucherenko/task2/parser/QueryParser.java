package ua.foxminded.kucherenko.task2.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class QueryParser {
    private QueryParser() {
    }

    public static String parseQuery(String filePath) {
        StringBuilder queryBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> queryBuilder.append(line).append(" "));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryBuilder.toString();
    }
}
