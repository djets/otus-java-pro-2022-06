package ru.otus.bankomat;

import java.util.Objects;

public class Banknote {
    private final String uid;
    private final BanknoteType type;
    private int nominal;

    public Banknote(BanknoteType type) {
        switch (type) {
            case ONE_HUNDRED -> this.nominal = 100;
            case ONE_THOUSAND -> this.nominal = 1000;
            case TWO_THOUSAND -> this.nominal = 2000;
            case FIVE_THOUSAND -> this.nominal = 5000;
        }
        this.type = type;
        this.uid = String.format("%09d", (int) (Math.random() * (1 - 1_000_000_000)) + 1);
    }

    public String getUid() {
        return uid;
    }

    public BanknoteType getType() {
        return type;
    }

    public int getNominal() {
        return nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banknote banknote = (Banknote) o;
        return uid.equals(banknote.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return "Banknote{" +
                ", name='" + type + '\'' +
                "uid='" + uid + '\'' +
                ", nominal=" + nominal +
                '}';
    }


}
