package ru.otus.bankomat;

import java.util.HashMap;
import java.util.Map;

public class LargeBillsConfigWadOfCashImpl implements ConfigWadOfCash {
    @Override
    //todo реализовать конфигуратор выдачи
    public Map<Cassette, Integer> getConfigWadOfCash(int sum, Map<BanknoteType, Integer> remainingBills) {
        Map<Cassette, Integer> largeBillsConfigWadOfCashImpl = new HashMap<>();
        return largeBillsConfigWadOfCashImpl;
    }
}
