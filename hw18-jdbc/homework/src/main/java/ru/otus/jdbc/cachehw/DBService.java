package ru.otus.jdbc.cachehw;

import java.util.List;
import java.util.Optional;

public interface DBService<T> {
    T saveOjt(T t);

    Optional<T> getOjt(long no);

    List<T> findAllOjt();
}
