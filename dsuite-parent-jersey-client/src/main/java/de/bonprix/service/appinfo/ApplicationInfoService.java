/**
 *
 */
package de.bonprix.service.appinfo;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Interface for provicing basic simple informatrions about an application. See
 * ApplicationInformation interface for more info.
 *
 * @author cthiel
 * @date 16.11.2016
 *
 */
@FunctionalInterface
@Path("/applicationinfo")
public interface ApplicationInfoService {

	/**
	 * Returns a list of all known application informations.
	 *
	 * @return
	 */
	@Path("")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<ApplicationInfoDto> get();
}
