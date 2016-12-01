package java.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/employee")
public class UserResource {
	@POST
	@Path("/add")
	public Response addEmployee(@FormParam("employeeName") String employeeName,
			@FormParam("password") int password) {

		return Response.status(200)
				.entity("addEmployee is requested, name of employee: " + employeeName + ", password: " + password)
				.build();
	}

}