package de.bonprix.base.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.base.demo.dto.Item;
import de.bonprix.model.ComplexFilter;
import de.bonprix.model.Paged;

/**
 * This is a demo service for showing how a simple CRUD would look like. It lists a dummy list of applications and can perform create, read, update and delete
 * operations on it.
 *
 * @author h.kalokhe
 * @date 20.06.17
 *
 */
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ItemService {

    /**
     * Paged read of all applications.
     *
     * @param pageable the pageable
     * @return a paged list of all applications
     */
    @GET
    @Path("/")
    List<Item> findAll(@BeanParam @Valid Paged pageable);

    /**
     * Load applications using a complex filter
     *
     * @param complexFilter used to define the filters
     * @return list of all found applications
     */
    @POST
    @Path("/filtered")
    List<Item> findByFilter(ComplexFilter complexFilter);

    /**
     * Returns the application with the given ID.
     *
     * @param id the ID
     * @return the application with the given ID
     * @responseMessage 404 when the application with the given ID was not found in the database
     */
    @GET
    @Path("/{id : \\d+}")
    Item findById(@PathParam("id") @NotNull Long id);

    /**
     * Deletes the application with the given ID.
     *
     * @param id the id
     * @responseMessage 404 when the application with the given ID was not found in the database
     */
    @DELETE
    @Path("/{id : \\d+}")
    void deleteById(@PathParam("id") @NotNull Long id);

    @PUT
    @Path("/")
    Item saveItem(@NotNull Item item);

}
