package jrack.api.rs.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import prop.core.patterns.cor.CORException;
import prop.engine.PatchMessage;
import prop.engine.PropRegistry;
import prop.engine.modes.PropModeResolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

// @Path("resourceType")
abstract public class PropController<R extends PropRegistry> {

	abstract protected R getRegistry();

	abstract protected JsonNode buildOperation(String operation, String id);

	abstract protected String getResourceType();

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("{id}/to/{op:[\\w]+}")
	@Produces("application/hal+json; charset=UTF-8")
	public Response map(@PathParam("id") String id,
			@PathParam("op") String operation) {
		ResponseBuilder response = Response.ok();
		ObjectNode entity = new ObjectMapper().createObjectNode();
		PatchMessage request = null;
		try {
			JsonNode knownOperation = buildOperation(operation, id);
			if (knownOperation == null) {
				response.status(Status.NOT_IMPLEMENTED);
				return response.build();
			}
			PropModeResolver mapper = getRegistry().getResolver("map");
			String resourceType = getResourceType();
			entity.put("resourceType", resourceType);
			entity.withArray(resourceType).add(knownOperation);
			request = new PatchMessage(getRegistry(), entity);
			mapper.process(request);
			entity = (ObjectNode) request.getEffectiveMessage();
		} catch (CORException e) {
			entity = request.getResponse().getErrors();
			response.status(Status.INTERNAL_SERVER_ERROR);
		}
		response.entity(entity);
		return response.build();
	}

}
