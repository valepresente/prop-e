package jrack.api.rs.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import prop.engine.PropRegistry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

// @Path("resourceType/operations")
abstract public class PropController<R extends PropRegistry> {

	abstract protected R getRegistry();

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response entrypoint() throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("jrack/api/rs/views/operations.json");
		ObjectNode entity = (ObjectNode) new ObjectMapper().readTree(in);
		for (String mode: getRegistry().getSupportedModes()) {
			entity.with("_links").with(mode + "_operation_schema")
					.put("href", "/operations/schemas/" + mode)
					.put("type", "application/schema+json");
		}
		ResponseBuilder response = Response.ok(entity);
		return response.build();
	}

}
