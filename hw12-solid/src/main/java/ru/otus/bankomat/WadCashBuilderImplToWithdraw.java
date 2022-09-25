package ru.otus.bankomat;
import java.util.List;
import java.util.Queue;

public class WadCashBuilderImplToWithdraw implements WadCashBuilder {
    @Override
    public WadOfCash createWadCash(int sum, Queue<BanknoteType> configWadOfCash, List<Cassette> cassettes) {
        WadOfCash wadOfCash = new WadOfCash(sum, configWadOfCash, cassettes);
        return wadOfCash;
    }
}
