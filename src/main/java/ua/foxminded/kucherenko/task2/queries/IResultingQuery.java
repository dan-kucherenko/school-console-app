package ua.foxminded.kucherenko.task2.queries;

public interface IResultingQuery<T1, T2> {
    T1 executeQueryWithRes(T2 data);
}
