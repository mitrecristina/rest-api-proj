package org.criss;

import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.criss.dao.AutoturismUserLinkDao;
import org.criss.dao.AutoturismeDao;
import org.criss.dao.UserDao;
import org.criss.exceptions.*;

import java.util.List;

@Path("/auto")
public class AutoturismeResource {
    @Inject
    DatabaseService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutoturisme() {
        List<AutoturismeDao> autoturismeDaoList = service.getAutoturisme();
        return Response.ok().entity(autoturismeDaoList).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buy")
    public Response buy(@Context HttpServerRequest request, AutoturismeDao dao) {
        String t = request.getHeader("token");
        try {
            String s = service.buy(t, dao);
        } catch (TokenExpirationException ex) {
            return Response.status(401).entity("Token Expired").build();
        } catch (UserNotFoundException ex) {
            return Response.status(401).entity("User Not Found").build();
        } catch (VehicleNotFoundException ex) {
            return Response.status(404).entity("Vehicle Not Found").build();
        } catch (VehicleOutOfStockException ex) {
            return Response.status(403).entity("Vehicle Out Of Stock").build();
        } catch (NotEnoughMoneyException ex) {
            return Response.status(403).entity("Not Enough Money").build();
        }


        return null;
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutoturismUser(@Context HttpServerRequest request) {
        String t = request.getHeader("token");
        try {
            List<AutoturismUserLinkDao> autoturismUserDaoList = service.getAutoturismUser(t);
            return Response.ok(autoturismUserDaoList).build();
        } catch (TokenExpirationException ex) {
            return Response.status(401).entity("Token Expired").build();
        } catch (UserNotFoundException ex) {
            return Response.status(401).entity("User Not Found").build();

        }
    }
}



