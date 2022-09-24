package ru.otus.bankomat;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cassette  {
    private BanknoteType type;

    private Queue<Banknote> banknotes;
    public Cassette(BanknoteType type) {
        this.type = type;
    }

    public BanknoteType getType() {
        return type;
    }

    public List<Banknote> present(int number){
      return banknotes.stream().limit(number).flatMap(b -> Stream.of(banknotes.poll())).collect(Collectors.toList());
    }

    public void receive(Banknote banknote) {
        banknotes.add(banknote);
    }
}
