package ru.otus.bankomat;

import java.util.ArrayList;
import java.util.List;

public class BanknoteFactoryImp implements BanknoteFactory {
    @Override
    public List<Banknote> create(int num, BanknoteType type) {
        var listBanknote = new ArrayList<Banknote>();
        for(int i = 0; i < num; i++){
            listBanknote.add(new Banknote(type));
        }
        return listBanknote;
    }
}
