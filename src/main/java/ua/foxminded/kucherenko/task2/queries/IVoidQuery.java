package ua.foxminded.kucherenko.task2.queries;

public interface IVoidQuery<T extends IQueryData> {
    void executeQuery(T data);
}
