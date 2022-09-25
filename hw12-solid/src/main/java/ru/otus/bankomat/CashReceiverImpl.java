package ru.otus.bankomat;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class CashReceiverImpl implements CashReceiver{
    private final List<Cassette> listCassettes;

    CashReceiverImpl(List<Cassette> cassettes) {
        this.listCassettes = cassettes;
    }
    @Override
    public void getAndPutCash(Queue<Banknote> banknoteList, Map<BanknoteType, Integer> remainingBills) {
        var sortedMapBanknotes = banknoteList.stream().collect(Collectors.groupingBy(Banknote::getType, Collectors.toList()));
        sortedMapBanknotes.forEach((key, value) -> listCassettes.stream().filter(cassette -> cassette.getType().equals(key))
                .forEach(cassette -> value.forEach(banknote -> {
                    cassette.receive(banknote);
                    remainingBills.computeIfPresent(cassette.getType(), (b, i) -> remainingBills.get(b) + 1);
                })));
        System.out.println("Amount deposited: " + banknoteList.stream().mapToInt(Banknote::getNominal).sum());
    }
    }