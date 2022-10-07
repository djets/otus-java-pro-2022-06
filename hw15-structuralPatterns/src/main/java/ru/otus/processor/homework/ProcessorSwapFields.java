package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class ProcessorSwapFields implements Processor {
    @Override
    public Message process(Message message) {
        var stateMessage = new Stack<Message>();
        stateMessage.push(message);
        return message.toBuilder().field11(message.getField12()).field12(stateMessage.pop().getField11()).build();
    }
}
