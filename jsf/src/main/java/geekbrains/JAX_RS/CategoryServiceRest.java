package geekbrains.JAX_RS;

import geekbrains.service.dao.CategoryDAO;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 08.07.2020
 * v1.0
 */
@Local
@Path("/categories")
public interface CategoryServiceRest {

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    List<CategoryDAO> findAll();

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    void insert(CategoryDAO category);

}
