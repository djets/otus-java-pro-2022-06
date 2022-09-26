package ru.otus.bankomat;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Stream;

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
        dispenser.cashLoad(banknoteLoadQueue);
        System.out.println("ATM init passed successfully \nCash balance :");
        dispenser.getRemainingBills().forEach((k,v) -> System.out.println(k.toString() + " - " + v));
        Stream.iterate(1, n-> n + 1).limit(4).forEach(integer -> {
            System.out.println("------------------------------------");
            int r = 1;
            while (r % 100 != 0) {
                r = ((int) (Math.random() * (1_000_000 - 100)) + 100);
            }
            dispenser.cashWithdrawal(r);
            System.out.println("------------------------------------\nCash balance :");
            dispenser.getRemainingBills().forEach((k,v) -> System.out.println(k.toString() + " - " + v));
        });
    }
}
