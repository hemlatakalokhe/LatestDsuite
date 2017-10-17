package de.bonprix.information;

import de.bonprix.dto.masterdata.SimpleClient;

/**
 * Spring bean helper class for providing information about clients itself.
 *
 * @author thacht
 * @date 10.02.2017
 *
 */
public interface ClientProvider {

	/**
	 * gives back information about a client found by id.
	 * 
	 * @return client
	 */
	SimpleClient getClient(Long clientId);

}
