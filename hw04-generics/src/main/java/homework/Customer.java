package homework;

public class Customer {
    private final long id;
    private String name;
    private long scores;

    //todo: 1. в этом классе надо исправить ошибки

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;

        return id == customer.id;
//        "Я не знаю как правильно - но это не правильно"(с)
//        if (scores != customer.scores) return false;
//        return Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
//        "Я не знаю как правильно - но это не правильно"(с)
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (int) (scores ^ (scores >>> 32));
        return (int) (id ^ (id >>> 32));
    }
}
