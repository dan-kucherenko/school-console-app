package ua.foxminded.kucherenko.task2.queries;

public interface IResultingQuery<U, V> {
    U executeQueryWithRes(V data);
}
