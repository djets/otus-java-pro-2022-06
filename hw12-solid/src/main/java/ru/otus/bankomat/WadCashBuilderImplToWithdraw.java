package ru.otus.bankomat;
import java.util.Map;

public class WadCashBuilderImplToWithdraw implements WadCashBuilder {
    @Override
    public WadOfCash createWadCash(Map<Cassette, Integer> configWadOfCash) {
        WadOfCash wadOfCash = new WadOfCash(configWadOfCash);
        return wadOfCash;
    }
}
