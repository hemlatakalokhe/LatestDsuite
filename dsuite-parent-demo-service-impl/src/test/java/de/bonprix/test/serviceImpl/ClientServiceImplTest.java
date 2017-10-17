package de.bonprix.test.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.jparepository.ClientRepository;
import de.bonprix.base.demo.model.ClientEntity;
import de.bonprix.base.demo.model.builder.ClientEntityBuilder;
import de.bonprix.base.demo.service.ClientServiceImpl;

public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    private ClientEntity clientEntity;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.clientEntity = new ClientEntityBuilder().withId(1L)
            .build();
    }

    @Test
    public void findAll() {
        final List<ClientEntity> clientEntities = Arrays.asList(this.clientEntity);
        Mockito.when(this.clientRepository.findAll())
            .thenReturn(clientEntities);
        final List<Client> clients = this.clientServiceImpl.findAll();
        Mockito.verify(this.clientRepository)
            .findAll();
        Assert.assertEquals(clientEntities.size(), clients.size());
    }

}
