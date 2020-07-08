package geekbrains.JAX_RS;

import geekbrains.persist.Product;
import geekbrains.service.dao.ProductDAO;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 07.07.2020
 * v1.0
 */
@Local
@Path(value = "products")
public interface ProductServiceRest {

    @GET
    @Path("/getAll")
    @Produces(value = MediaType.APPLICATION_JSON)
    List<ProductDAO> findAll();

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductDAO findById(@PathParam("id") Long id);

    @GET
    @Path("/getByName/{name}")
    @Produces(value = MediaType.APPLICATION_JSON)
    List<ProductDAO> findByName(@PathParam("name")String name);

    @GET
    @Path("/getByCategoryId/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    List<ProductDAO> findByCategoryId(@PathParam("id")Long id);

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(ProductDAO prod);

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(ProductDAO prod);

    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(@PathParam("id") Long id);

}
