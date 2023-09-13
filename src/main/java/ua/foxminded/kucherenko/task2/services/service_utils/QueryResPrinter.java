package ua.foxminded.kucherenko.task2.services.service_utils;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueryResPrinter {
    public String printResults(List<?> results) {
        return results.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
