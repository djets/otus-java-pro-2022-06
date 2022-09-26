package ru.otus.bankomat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WadOfCash {
    private final Map<BanknoteType, List<Banknote>> wadOfCash;
    private int rest;
    public WadOfCash(Banknote ... banknotes){
        this.wadOfCash = new HashMap<>(Stream.of(banknotes).collect(Collectors.groupingBy(Banknote::getType, Collectors.toList())));
    }
    public WadOfCash(int sum, Queue<BanknoteType> configWadOfCash, List<Cassette> cassettes){
        this.wadOfCash = new HashMap<>();
        rest = sum;
        for(var entry : configWadOfCash) {

            cassettes.stream().filter(cassette -> cassette.getType().equals(entry))
                    .forEach(cassette -> {
                        int nominal = cassette.getBanknotes().peek().getNominal();
                        if(nominal <= rest) {
                            int n = rest / nominal;
                            int count = 0;
                            while (cassette.getBanknotes().size() > 0 && n > 0) {
                                n--;
                                count++;
                            }
                            wadOfCash.put(entry, cassette.present(count));
                            rest = rest - nominal * count;
                        }
                    });
        }
        System.out.println("Wad of cash - complete:");
        wadOfCash.forEach((s, l) -> System.out.println(s.toString() + ": " + l.size() + " = " + l.size() * l.get(0).getNominal()));
    }
    public Map<BanknoteType, List<Banknote>> getWadCash() {
        return wadOfCash;
    }
}
