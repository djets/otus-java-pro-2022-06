package ru.otus.bankomat;

import java.util.List;
import java.util.Map;

public class Dispenser {

    private Map<String, Integer> remainingBills;

    private List<Cassette> cassettes;

    public List<Cassette> getCassettes() {
        return cassettes;
    }

    public Map<String, Integer> getRemainingBills() {
        return remainingBills;
    }

    public void setRemainingBills(Map<String, Integer> remainingBills) {
        this.remainingBills = remainingBills;
    }

    public void cashWithdrawal(int sum){
        var presenter = new PresenterImpl();
        var wadCashBuilder = new WadCashBuilderImplToWithdraw();
        var configWadOfCash = new LargeBillsConfigWadOfCashImpl();
        var wadCash = wadCashBuilder.createWadCash(configWadOfCash.getConfigWadOfCash(sum, getRemainingBills()));
        presenter.withdraw(wadCash);
    }


}
