package ru.otus.bankomat;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface CashReceiver {
    public void getAndPutCash(Queue<Banknote> banknoteList, Map<BanknoteType, Integer> remainingBills);
}
