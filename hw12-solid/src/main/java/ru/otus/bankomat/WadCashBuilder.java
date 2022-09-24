package ru.otus.bankomat;

import java.util.Map;

public interface WadCashBuilder {
    public WadOfCash createWadCash(Map<Cassette, Integer> remainingBills);
}
