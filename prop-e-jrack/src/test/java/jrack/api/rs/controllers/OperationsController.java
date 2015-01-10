package jrack.api.rs.controllers;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import prop.engine.OperationsRegistry;

@Path("operations")
public class OperationsController extends PropController<OperationsRegistry> {

	@Autowired
	private OperationsRegistry registry;

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response entrypoint() throws IOException {
		return super.entrypoint();
	}

	@Override
	protected OperationsRegistry getRegistry() {
		return registry;
	}

	@Override
	protected UriInfo getUriInfo() {
		return uriInfo;
	}

}
