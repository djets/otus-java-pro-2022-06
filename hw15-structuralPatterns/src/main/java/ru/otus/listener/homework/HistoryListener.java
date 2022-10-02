package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.*;
import java.util.stream.Collectors;

public class HistoryListener implements Listener, HistoryReader {
    Map<Long, Message> messageStack = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        try {
//            var safeField13 = new ObjectForMessage();
//            safeField13.setData(List.copyOf(msg.getField13().getData()));
            var copyMsg = new Message.Builder(msg.getId()).build();

            Arrays.stream(msg.getClass().getDeclaredFields()).forEach(f -> {
                f.setAccessible(true);
                try {
                    if (f.get(msg) != null) {
                      var f1 = copyMsg.getClass().getDeclaredField(f.getName());
                      f1.setAccessible(true);
                      if(f.getName().equals("field13")){
                          var safeField13 = new ObjectForMessage();
                          safeField13.setData(List.copyOf(msg.getField13().getData()));
                          f1.set(copyMsg, safeField13);
                      } else {
                          f1.set(copyMsg, f.get(msg));
                      }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });
            messageStack.put(copyMsg.getId(), copyMsg);
        } catch (Exception e) {
        throw new UnsupportedOperationException();
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        try {
            return Optional.of(messageStack.get(id));
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
    }
}
