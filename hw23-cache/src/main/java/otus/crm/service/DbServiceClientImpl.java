package otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.cachehw.HwCache;
import otus.cachehw.HwListener;
import otus.cachehw.MyCache;
import otus.crm.model.Client;
import otus.core.repository.DataTemplate;
import otus.core.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private HwCache<String, Client> myCache = new MyCache<>();
    private final HwListener<String, Client> listener = new HwListener<>() {
        @Override
        public void notify(String key, Client value, String action) {
            log.info("key: {}, client: {}, action: {}", key, value.getName(), action);
        }
    };

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        myCache.addListener(listener);
    }

    @Override
    public Client saveClient(Client clients) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = clients.clone();
            if (clients.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            myCache.put(getKey(clientCloned.getId()), clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        //find MyCache
        var client = myCache.get(getKey(id));
        if (client != null) {
            return Optional.ofNullable(client);
        } else {
            return transactionManager.doInReadOnlyTransaction(session -> {
                var clientOptional = clientDataTemplate.findById(session, id);
                log.info("client: {}", clientOptional);
                myCache.put(getKey(id), clientOptional.get());
                return clientOptional;
            });
        }
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            //Add MyCache
            clientList.forEach(client -> myCache.put(String.valueOf(client.getId()), client));
            return clientList;
        });
    }

    private static String getKey(long id) {
        return String.valueOf(id);
    }
}
