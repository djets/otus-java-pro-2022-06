package ru.otus.bankomat;

import java.util.stream.IntStream;

public class PresenterImpl implements Presenter {
    @Override
    public void withdraw(WadOfCash wadOfCash) {
        System.out.println("Present: " );
        wadOfCash.getWadCash().forEach((s, l) -> System.out.println(s + l.size() * l.get(0).getNominal()));
        wadOfCash = null;
    }
}
