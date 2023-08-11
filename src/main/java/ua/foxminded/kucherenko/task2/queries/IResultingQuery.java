package ua.foxminded.kucherenko.task2.queries;

import java.sql.ResultSet;

public interface IResultingQuery<T1, T2> {
    T1 executeQueryWithRes(T2 data);

    T1 parseResultSet(ResultSet resultSet);
}
