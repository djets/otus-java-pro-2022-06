package ru.otus.bankomat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WadOfCash {
    private final Map<BanknoteType, List<Banknote>> wadOfCash;

    public WadOfCash(Banknote ... banknotes){
        this.wadOfCash = new HashMap<>(Stream.of(banknotes).collect(Collectors.groupingBy(Banknote::getType, Collectors.toList())));
    }
    public WadOfCash(Map<Cassette, Integer> configWadOfCash){
        this.wadOfCash = new HashMap<>();
        configWadOfCash.forEach((key, value) -> wadOfCash.put(key.getType(), key.present(value)));
        System.out.println("Wad of cash - complete:\n");
        wadOfCash.forEach((s, l) -> System.out.println(s.toString() + l.size() * l.get(0).getNominal()));
    }
    public Map<BanknoteType, List<Banknote>> getWadCash() {
        return wadOfCash;
    }
}
