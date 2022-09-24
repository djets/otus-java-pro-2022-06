package ru.otus.bankomat;

import java.util.Map;

public interface ConfigWadOfCash {
    public Map<Cassette, Integer> getConfigWadOfCash(int sum, Map<BanknoteType, Integer> remainingBills);
}
