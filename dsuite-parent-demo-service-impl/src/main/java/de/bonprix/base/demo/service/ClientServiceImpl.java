package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.builder.ClientBuilder;
import de.bonprix.base.demo.jparepository.ClientRepository;
import de.bonprix.base.demo.model.ClientEntity;

@RestService
@Transactional
public class ClientServiceImpl implements ClientService {

    @Resource
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        final List<Client> clients = new ArrayList<>();
        for (final ClientEntity client : this.clientRepository.findAll()) {
            clients.add(convertToDto(client));
        }
        return clients;
    }

    private Client convertToDto(final ClientEntity clientEntity) {

        return new ClientBuilder().withId(clientEntity.getId())
            .withClientName(clientEntity.getClientName())
            .build();
    }

}
