package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> messageStack = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        messageStack.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        try {
            return Optional.of(messageStack.get(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
