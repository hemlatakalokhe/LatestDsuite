package de.bonprix.security;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.testng.annotations.Test;

import de.bonprix.dto.masterdata.SimpleClient;
import de.bonprix.dto.masterdata.builder.SimpleClientBuilder;
import de.bonprix.information.ClientProvider;
import de.bonprix.information.ClientProviderImpl;
import de.bonprix.information.service.SimpleClientService;

public class ClientProviderImplTest {

	@Test
	public void getClientTest() throws IllegalArgumentException, IllegalAccessException {
		long clientId = 1L;

		SimpleClientService clientServiceMock = Mockito.mock(SimpleClientService.class);

		ClientProvider clientProvider = new ClientProviderImpl();
		MemberModifier.field(ClientProviderImpl.class, "dsuiteClientService")
			.set(clientProvider, clientServiceMock);

		clientProvider.getClient(clientId);

		Mockito.verify(clientServiceMock)
			.findById(Mockito.eq(clientId));
	}

	@Test
	public void getClientWithInitializedClientTest() throws IllegalArgumentException, IllegalAccessException {
		SimpleClient client = new SimpleClientBuilder().withId(1L)
			.build();
		Map<Long, SimpleClient> clientMap = new HashMap<>();
		clientMap.put(client.getId(), client);

		SimpleClientService clientServiceMock = Mockito.mock(SimpleClientService.class);

		ClientProvider clientProvider = new ClientProviderImpl();
		MemberModifier.field(ClientProviderImpl.class, "dsuiteClientService")
			.set(clientProvider, clientServiceMock);
		MemberModifier.field(ClientProviderImpl.class, "clientMap")
			.set(clientProvider, clientMap);

		clientProvider.getClient(client.getId());

		Mockito.verify(clientServiceMock, Mockito.never())
			.findById(Mockito.anyLong());
	}
}
