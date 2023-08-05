package ua.foxminded.kucherenko.task2.queries;

public interface IResultingQuery<T> extends IQuery {
    T executeQueryWithRes();
}
