package ru.otus.bankomat;

public class OneHundredRubles extends Banknote {
    private final String uid;
    private final String name = "oneHundredRubles";
    private final int nominal = 100;


    public OneHundredRubles() {
        this.uid = super.getUid();
    }
}
