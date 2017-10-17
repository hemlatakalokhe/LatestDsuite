package de.bonprix.information;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import de.bonprix.dto.masterdata.SimpleClient;
import de.bonprix.information.service.SimpleClientService;

@Component
public class ClientProviderImpl implements ClientProvider {

	@Resource(name = "dsuiteClientService")
	private SimpleClientService dsuiteClientService;

	Map<Long, SimpleClient> clientMap = new HashMap<>();

	@Override
	public SimpleClient getClient(Long clientId) {
		if (!this.clientMap.containsKey(clientId)) {
			SimpleClient client = this.dsuiteClientService.findById(clientId);
			this.clientMap.put(clientId, client);
		}
		return this.clientMap.get(clientId);
	}

}
