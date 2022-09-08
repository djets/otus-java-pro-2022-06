package homework;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    final LinkedHashMap<Customer, String> mapCustomerService = new LinkedHashMap<>();
    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        NavigableMap<Customer,String> sortedMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
        //До последнего думал что так делать не надо, но вариантов у меня не осталось.
        mapCustomerService.forEach((k,v)-> sortedMap.put(new Customer(k.getId(), k.getName(), k.getScores()), v));
//        sortedMap.putAll(mapCustomerService);
//      return sortedMapCustomerService.entrySet().stream().limit(1).iterator().next();
        return sortedMap.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        NavigableMap<Customer,String> sortedMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
        //До последнего думал что так делать не надо, но вариантов у меня не осталось.
        mapCustomerService.forEach((k,v)-> sortedMap.put(new Customer(k.getId(), k.getName(), k.getScores()), v));
        return sortedMap.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        mapCustomerService.put(customer, data);
    }
}
