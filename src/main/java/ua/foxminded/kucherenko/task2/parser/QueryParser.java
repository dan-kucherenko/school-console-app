package ua.foxminded.kucherenko.task2.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QueryParser {
    private QueryParser(){}
    public static String parseQuery(String filePath) {
        StringBuilder queryBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                    queryBuilder.append(line).append(" ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryBuilder.toString();
    }
}
