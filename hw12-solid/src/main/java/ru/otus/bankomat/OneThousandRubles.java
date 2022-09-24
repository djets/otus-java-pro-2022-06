package ru.otus.bankomat;

public class OneThousandRubles extends Banknote {
    private final String uid;
    private final String name = "oneThousandRubles";
    private int nominal = 1000;

    public OneThousandRubles() {
        this.uid = super.getUid();
    }
}
