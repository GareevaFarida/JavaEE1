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
public class EJBTransactionRolledbackMapper implements ExceptionMapper<EJBTransactionRolledbackException> {

    @Override
    public Response toResponse(EJBTransactionRolledbackException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();
    }

    private String prepareMessage(EJBTransactionRolledbackException exception) {

        String m = exception.getMessage();
        String r = "Invalid request.\n";
        r += String.format("Error Message: %s.%nError Type: %s.%n",
                m, exception.getClass());
        return r;
    }
}
