package ru.otus.bankomat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class BanknoteFactoryImp implements BanknoteFactory {
    @Override
    public Queue<Banknote> create(int num, BanknoteType type) {
        var listBanknote = new ArrayDeque<Banknote>();
        for(int i = 0; i < num; i++){
            listBanknote.add(new Banknote(type));
        }
        return listBanknote;
    }
}
