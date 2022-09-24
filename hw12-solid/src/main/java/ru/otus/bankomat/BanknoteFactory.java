package ru.otus.bankomat;

import java.util.List;
import java.util.Queue;

public interface BanknoteFactory {
    public Queue<Banknote> create(int num, BanknoteType type);
}
