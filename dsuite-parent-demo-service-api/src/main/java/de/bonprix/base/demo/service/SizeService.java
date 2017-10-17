package de.bonprix.base.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.base.demo.dto.Size;
import de.bonprix.model.Paged;

/**
 * This is a demo service for showing how a simple CRUD would look like. It lists a dummy list of applications and can perform create, read, update and delete
 * operations on it.
 *
 * @author h.kalokhe
 * @date 20.06.17
 *
 */
@Path("/sizes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SizeService {

    /**
     * Paged read of all applications.
     *
     * @param pageable the pageable
     * @return a paged list of all applications
     */
    @GET
    @Path("/")
    List<Size> findAll(@BeanParam @Valid Paged pageable);

    /**
     * Load applications using a complex filter
     *
     * @param complexFilter used to define the filters
     * @return list of all found applications
     */

}
