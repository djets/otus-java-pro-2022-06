package ru.otus.bankomat;

import java.util.HashMap;
import java.util.Map;

public class LargeBillsConfigWadOfCashImpl implements ConfigWadOfCash {
    @Override
    public Map<Cassette, Integer> getConfigWadOfCash(int sum, Map<String, Integer> remainingBills) {
        Map<Cassette, Integer> largeBillsConfigWadOfCashImpl = new HashMap<>();
        return largeBillsConfigWadOfCashImpl;
    }
}
