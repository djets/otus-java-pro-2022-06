package ru.otus.processor.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProcessorEvenSecondsTest {
    @DisplayName("Проверяем бросок исключения")
    @Test()
    void process() {
        var test = new ProcessorEvenSeconds(2L);
        var message = new Message.Builder(1L)
                .field1("field1")
                        .build();
        assertThrows(RuntimeException.class, () -> test.process(message));
//        assertThatThrownBy(() -> test.process(message)).isInstanceOf(RuntimeException.class);
    }
}