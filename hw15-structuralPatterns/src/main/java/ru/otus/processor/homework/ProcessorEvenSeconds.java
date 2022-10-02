package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.sql.Time;
import java.util.*;

public class ProcessorEvenSeconds implements Processor {
    long evenSecond;
    public ProcessorEvenSeconds() {
        evenSecond = new Date().getTime();
    }
    public ProcessorEvenSeconds(long setTime) {
        this.evenSecond = setTime;
    }
    @Override
    public Message process(Message message) {
        if ((evenSecond / 60) % 2 == 0) {
            throw new RuntimeException("Четная");
        }
        return null;
    }
}
