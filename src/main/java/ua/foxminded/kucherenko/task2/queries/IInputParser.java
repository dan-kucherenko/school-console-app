package ua.foxminded.kucherenko.task2.queries;

public interface IInputParser<T extends IQueryData> {
    T passData();
}
