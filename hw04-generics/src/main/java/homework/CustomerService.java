package homework;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    Map<Customer, String> mapCustomerService = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
//    Map<Customer, String> mapCustomerService = new LinkedHashMap<>();
    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
//        mapCustomerService.entrySet().stream().limit(1).forEach(m-> System.out.println(m.getKey().getName()));
//        Map.Entry<Customer, String> n = mapCustomerService.entrySet().stream().limit(1).collect();
//        System.out.println(n.getKey().getName());
//        Map<Customer, String> sortedMapCustomerService = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
//        sortedMapCustomerService.putAll(mapCustomerService);

//        sortedMapCustomerService.forEach((k, v) -> System.out.println(k.getName() + " " + v));
//        return sortedMapCustomerService.entrySet().stream().limit(1).iterator().next(); // это "заглушка, чтобы скомилировать"
        return mapCustomerService.entrySet().stream().limit(1).iterator().next();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        /**Iterator<Map.Entry<Customer, String>> itr = mapCustomerService.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Customer, String> entry = itr.next();
            if (entry.equals(customer))
                return itr.next();
        }
         */
        customer.getScores();
//        mapCustomerService.put(customer, "someData");
        return null; // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
//        mapCustomerService.put(customer, data);
        mapCustomerService.put(customer, data);
    }
}
