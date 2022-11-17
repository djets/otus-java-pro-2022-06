package ru.otus.homework;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import otus.crm.model.Address;
import otus.crm.model.Client;
import otus.crm.model.Phone;
import ru.otus.base.AbstractHibernateTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование MyCache:")
class DbServiceClientsWithMyCacheTest extends AbstractHibernateTest {
    Logger logger = (Logger) LoggerFactory.getLogger(DbServiceClientsWithMyCacheTest.class);

    protected List<Client> clients;

    @Test
    @DisplayName("проверяем заполнение MyCache")
    void checkingCacheFilingTest() {
        getClients(10);

        var savedClients = clients.stream().peek(client -> dbServiceClient.saveClient(client)).peek(client -> System.out.println(client)).collect(Collectors.toList());

        var loadedAllClients = dbServiceClient.findAll();

        dbServiceClient.getClient(loadedAllClients.get(0).getId()).get().setName("NewName");
        dbServiceClient.saveClient(new Client("NewClient2"));

        loadedAllClients = dbServiceClient.findAll();

        var selectAllClients = loadedAllClients.stream().map(client -> dbServiceClient.getClient(client.getId())).map(clientOptional -> clientOptional.get()).collect(Collectors.toList());
        assertThat(loadedAllClients).usingRecursiveComparison().isEqualTo(selectAllClients);

        var cC = dbServiceClient.getClient(loadedAllClients.get(0).getId());
    }

    @Test
    @DisplayName("оцениваем скорость работы MyCache")
    void rateMyCacheTest() {
        getClients(50);
        var savedClients = clients.stream().peek(client -> dbServiceClient.saveClient(client)).peek(client -> System.out.println(client)).collect(Collectors.toList());

        long startSelect = System.currentTimeMillis();
        Stream.iterate(1, n -> n + 1).limit(10).map(i -> dbServiceClient.getClient(i)).collect(Collectors.toList());
        long stopSelect = System.currentTimeMillis();
        logger.info("execution time: {}", stopSelect - startSelect);
        long startSelectMyCache = System.currentTimeMillis();
        Stream.iterate(1, n -> n + 1).limit(10).map(i -> dbServiceClient.getClient(i)).collect(Collectors.toList());
        long stopSelectMyCache = System.currentTimeMillis();
        logger.info("execution time: {}", stopSelect - startSelect);

        assertThat(stopSelect - startSelect).isGreaterThan(stopSelectMyCache - startSelectMyCache);
    }

    @Test
    @DisplayName("тестируем переполнеие MyCache")
    void MyCacheTest() {
        getClients(10);
        clients.stream().peek(client -> dbServiceClient.saveClient(client)).peek(client -> System.out.println(client)).collect(Collectors.toList());
        logger.info("client loaded from DB: {}\n", dbServiceClient.getClient(9).get());
        logger.info("client loaded from <MyCache>: {}\n", dbServiceClient.getClient(9).get());
        System.gc();

        logger.info("client loaded from DB: {}\n", dbServiceClient.getClient(9).get());
    }

    private List<Client> getClients(int count) {
        clients = new ArrayList<Client>();
        for (int i = 0; i <= count; i++) {
            clients.add(new Client(null, "Name" + i, new Address(null, "SomeAddress" + i), List.of(new Phone(null, String.valueOf(i)))));
        }
        return clients;
    }
}