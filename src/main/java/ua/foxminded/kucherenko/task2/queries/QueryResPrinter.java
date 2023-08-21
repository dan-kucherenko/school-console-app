package ua.foxminded.kucherenko.task2.queries;

import java.util.List;
import java.util.stream.Collectors;

public class QueryResPrinter {
    public String printResults(List<?> results) {
        return results.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
