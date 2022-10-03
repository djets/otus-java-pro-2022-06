package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalTime;

public class ProcessorEvenSeconds implements Processor {
    long evenSecond;
    public ProcessorEvenSeconds() {
        evenSecond = LocalTime.now().getSecond();
    }
    public ProcessorEvenSeconds(long setTime) {
        this.evenSecond = setTime;
    }
    @Override
    public Message process(Message message) {
        if (evenSecond % 2 == 0) {
            throw new RuntimeException("Четная секунда");
        }
        return null;
    }
}
