package ru.otus.bankomat;

public class Application {
    public static void main(String[] args) {
        BanknoteFactory banknoteFactory = new BanknoteFactoryImp();
        var holder100 = banknoteFactory.create(2000,BanknoteType.ONE_HUNDRED);
        var holder1000 = banknoteFactory.create(2000,BanknoteType.ONE_THOUSAND);
        var holder2000 = banknoteFactory.create(2000,BanknoteType.TWO_THOUSAND);
        var holder5000 = banknoteFactory.create(2000,BanknoteType.FIVE_THOUSAND);
        Dispenser dispenser = new Dispenser();
    }
}
