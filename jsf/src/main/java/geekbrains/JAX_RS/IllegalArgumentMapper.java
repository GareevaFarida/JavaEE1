package geekbrains.JAX_RS;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @Author Farida Gareeva
 * Created 08.07.2020
 * v1.0
 */
@Provider
public class IllegalArgumentMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();
    }

    private String prepareMessage(IllegalArgumentException exception) {

        String m = exception.getMessage();
        String r = "Invalid request.\n";
        r += String.format("Error Message: %s.%nError Type: %s.%n",
                m, exception.getClass());
        return r;
    }
}
