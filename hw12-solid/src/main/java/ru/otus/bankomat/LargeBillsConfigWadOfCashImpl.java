package ru.otus.bankomat;

import java.util.*;

public class LargeBillsConfigWadOfCashImpl implements ConfigWadOfCash {
    @Override
    public Queue<BanknoteType> getConfigWadOfCash() {
        return new ArrayDeque<>(Arrays.asList(BanknoteType.FIVE_THOUSAND, BanknoteType.TWO_THOUSAND, BanknoteType.ONE_THOUSAND, BanknoteType.ONE_HUNDRED));
    }
}
