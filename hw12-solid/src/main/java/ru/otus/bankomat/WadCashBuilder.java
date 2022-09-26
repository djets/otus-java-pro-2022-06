package ru.otus.bankomat;

import java.util.List;
import java.util.Queue;

public interface WadCashBuilder {
    WadOfCash createWadCash(int sum, Queue<BanknoteType> remainingBills, List<Cassette> cassettes);
}
