package org.criss;

import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.criss.dao.UserDao;
import org.criss.exceptions.UserNotFoundException;
import java.util.List;


@Path("/user")
public class UsersResource {
    @Inject
    DatabaseService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserwithToken(UserDao userDao) {

        Long id = service.createUserwithToken(userDao);
        if (id != null) {
            return Response.ok(id).build();
        } else {
            return Response.status(409).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<UserDao> userDaoList = service.getUsers();
        return Response.ok().entity(userDaoList).build();
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDao userDao) {

        String token = service.login(userDao.username, userDao.password);
        if (token != null) {
            return Response.ok(token).build();
        } else {
            return Response.status(401).build();
        }
    }

    @Path("/refresh")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(@Context HttpServerRequest request) {
        String t = request.getHeader("token");
        try {
            String s = service.refreshToken(t);
            return Response.ok(s).build();
        } catch (UserNotFoundException ex) {
            return Response.status(401).entity("User Not Found").build();
        }
    }
}





