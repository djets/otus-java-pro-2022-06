package ru.otus.bankomat;

import java.util.*;

public class Dispenser {

    private final Presenter presenter;
    private final CashReceiverImpl cashReceiver;
    private final List<Cassette> cassettes;
    private Map<BanknoteType, Integer> remainingBills;

    Dispenser() {
        this.presenter = new PresenterImpl();
        this.cassettes = new ArrayList<>(Arrays.asList(new Cassette(BanknoteType.ONE_HUNDRED), new Cassette(BanknoteType.ONE_THOUSAND), new Cassette(BanknoteType.TWO_THOUSAND), new Cassette(BanknoteType.FIVE_THOUSAND)));
        this.cashReceiver = new CashReceiverImpl(cassettes);
        this.remainingBills = new HashMap<>();
        Arrays.stream(BanknoteType.values()).forEach((t-> remainingBills.put(t, 0)));
    }

    public List<Cassette> getCassettes() {
        return cassettes;
    }

    public Map<BanknoteType, Integer> getRemainingBills() {
        return remainingBills;
    }

    public void setRemainingBills(Map<BanknoteType, Integer> remainingBills) {
        this.remainingBills = remainingBills;
    }

    public void cashWithdrawal(int sum){
        var wadCashBuilder = new WadCashBuilderImplToWithdraw();
        var configWadOfCash = new LargeBillsConfigWadOfCashImpl();
        var wadCash = wadCashBuilder.createWadCash(configWadOfCash.getConfigWadOfCash(sum, getRemainingBills()));
        presenter.withdraw(wadCash);
    }

    public void cashReceive(Queue<Banknote> cashList){
        cashReceiver.getAndPutCash(cashList, getRemainingBills());
    }

}
