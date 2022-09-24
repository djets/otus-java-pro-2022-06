package ru.otus.bankomat;

import java.util.Objects;

public class Banknote {
    private final String uid;
    private final BanknoteType type = null;
    private final int nominal = 0;

    public Banknote(BanknoteType type) {
        this.uid = String.format("%10", (int) (Math.random() * (1 - 1_000_000_000)) + 1);
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
