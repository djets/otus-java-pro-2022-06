package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSeconds implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSeconds(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if(dateTimeProvider.getDate().getSecond() %2 == 0)
            throw new RuntimeException();
        return null;
    }
}
