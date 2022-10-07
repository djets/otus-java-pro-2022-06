package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessorEvenSecondsTest {
    @DisplayName("Проверяем бросок исключения")
    @Test()
    void process() {
        var dateTimeProvider = mock(DateTimeProvider.class);
        when(dateTimeProvider.getDate()).thenReturn(LocalDateTime.of(2022, 10, 7, 12, 1,2));
        var message = new Message.Builder(1L)
                .field1("field1")
                        .build();
        assertThrows(RuntimeException.class, () -> new ProcessorEvenSeconds(dateTimeProvider).process(message));
//        assertThatThrownBy(() -> test.process(message)).isInstanceOf(RuntimeException.class);
    }
}