package ru.otus.bankomat;

import java.util.ArrayDeque;
import java.util.Queue;

public class Application {
    public static void main(String[] args) {
        BanknoteFactory banknoteFactory = new BanknoteFactoryImp();
        var holder100 = banknoteFactory.create(2000,BanknoteType.ONE_HUNDRED);
        var holder1000 = banknoteFactory.create(2000,BanknoteType.ONE_THOUSAND);
        var holder2000 = banknoteFactory.create(2000,BanknoteType.TWO_THOUSAND);
        var holder5000 = banknoteFactory.create(2000,BanknoteType.FIVE_THOUSAND);
        System.out.println("Init loading of the ATM");
        Dispenser dispenser = new Dispenser();
        Queue<Banknote> banknoteLoadQueue = new ArrayDeque<>();
        for (int i = 0; i <1500; i++) {
            banknoteLoadQueue.add(holder100.poll());
            banknoteLoadQueue.add(holder1000.poll());
            banknoteLoadQueue.add(holder2000.poll());
            banknoteLoadQueue.add(holder5000.poll());
        }
        dispenser.cashReceive(banknoteLoadQueue);
        System.out.println("ATM init passed successfully \nCash balance :");
        dispenser.getRemainingBills().forEach((k,v) -> System.out.println(k.toString() + " - " + v));
    }
}
