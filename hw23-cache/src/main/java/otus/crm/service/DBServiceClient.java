package otus.crm.service;

import otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client clients);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
