package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.models.Student;

import java.sql.ResultSet;
import java.util.List;

public interface IResultingQuery<T> {
    T executeQueryWithRes();
    T parseResultSet(ResultSet resultSet);
}
