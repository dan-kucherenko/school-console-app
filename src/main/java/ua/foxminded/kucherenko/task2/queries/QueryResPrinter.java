package ua.foxminded.kucherenko.task2.queries;

import java.util.List;

public class QueryResPrinter {
    public String printResults(List<?> results) {
        StringBuilder res = new StringBuilder();
        results.forEach(element -> res.append(element).append('\n'));
        return res.toString();
    }
}
