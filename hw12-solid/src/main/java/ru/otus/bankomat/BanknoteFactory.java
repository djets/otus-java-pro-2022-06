package ru.otus.bankomat;

import java.util.List;

public interface BanknoteFactory {
    public List<Banknote> create(int num, BanknoteType type);
}
