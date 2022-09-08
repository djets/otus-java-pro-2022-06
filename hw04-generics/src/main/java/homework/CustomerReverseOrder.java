package homework;


import java.util.*;

public class CustomerReverseOrder {
    protected ArrayDeque<Customer> customerMap = new ArrayDeque<>();
    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        customerMap.add(customer);
    }

    public Customer take() {
        return customerMap.pollLast();
        //return null; // это "заглушка, чтобы скомилировать"
    }
}
