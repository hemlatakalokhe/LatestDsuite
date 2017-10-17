package de.bonprix.i18n.service;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.i18n.dto.SimpleI18NKey;
import de.bonprix.i18n.service.fetch.SimpleI18NKeyFetchOptions;
import de.bonprix.i18n.service.filter.SimpleI18NKeyFilter;

@Path("/i18nkeys")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SimpleI18NKeyService {

	/**
	 * find all i18nkeys with filter and fetch options
	 * 
	 * @param filter
	 * @param fetchOptions
	 * @return list of applications
	 */
	@GET
	@Path("/")
	List<SimpleI18NKey> findAll(@BeanParam @Valid SimpleI18NKeyFilter filter,
			@BeanParam @Valid SimpleI18NKeyFetchOptions fetchOptions);

}
