package ua.foxminded.kucherenko.task2.services.service_utils;

public interface IInputParser<T extends IQueryData> {
    T passData();
}
