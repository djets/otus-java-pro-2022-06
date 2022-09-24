package ru.otus.bankomat;

public class PresenterImpl implements Presenter {
    @Override
    public void withdraw(WadOfCash wadOfCash) {
        System.out.println("Present: " );
        //todo должен обновлятся остаток в кассетах
        wadOfCash.getWadCash().forEach((s, l) -> System.out.println(s.toString() + l.size() * l.get(0).getNominal()));
        wadOfCash = null;
    }
}
