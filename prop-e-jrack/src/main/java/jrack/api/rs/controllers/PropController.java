package jrack.api.rs.controllers;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import jrack.api.rs.PATCH;
import prop.core.patterns.cor.CORException;
import prop.engine.PatchMessage;
import prop.engine.PropRegistry;
import prop.engine.modes.PropModeResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
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

	@Context
	private HttpHeaders headers;

	@GET
	@Path("{id}/operations/to/{op:[\\w]+}")
	@Produces("application/hal+json; charset=UTF-8")
	public Response map(@PathParam("id") String id,
			@PathParam("op") String operation) {
		ResponseBuilder response = Response.ok();
		ObjectNode entity = new ObjectMapper().createObjectNode();
		PatchMessage request = null;
		try {
			JsonNode knownOperation = buildOperation(operation, id);
			if (knownOperation == null) {
				response.status(Status.NOT_FOUND);
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

	@PATCH
	@Path("{id}/operations/to/{op:[\\w]+}")
	@Produces("application/hal+json; charset=UTF-8")
	public Response execute(@PathParam("id") String id,
			@PathParam("op") String operation, String body) {
		ResponseBuilder response = Response.ok();
		ObjectMapper jsonMapper = new ObjectMapper();
		ObjectNode entity;
		if (body == null || body.length() == 0) {
			entity = jsonMapper.createObjectNode();
			entity.put("resourceType", "error").with("error")
					.put("message", "Empty body");
			response.entity(entity).status(Status.BAD_REQUEST);
			return response.build();
		}
		PatchMessage request = null;
		try {
			PropModeResolver executor = getRegistry().getResolver("execute");
			if (executor == null) {
				entity = jsonMapper.createObjectNode();
				entity.put("resourceType", "error").with("error")
						.put("message", "No executor resolver");
				response.entity(entity).status(Status.NOT_IMPLEMENTED);
				return response.build();
			}
			entity = (ObjectNode) jsonMapper.readTree(body);
			request = new PatchMessage(getRegistry(), entity);
			Map<String, Cookie> cookies = headers.getCookies();
			if(!cookies.isEmpty()) {
				request.put("Cookies", cookies);
			}
			executor.process(request);
			response.entity(entity);
		} catch (JsonProcessingException e) {
			entity = jsonMapper.createObjectNode();
			entity.put("resourceType", "error").with("error")
					.put("message", e.getOriginalMessage());
			response.entity(entity).status(Status.BAD_REQUEST);
		} catch (IOException e) {
			entity = jsonMapper.createObjectNode();
			entity.put("resourceType", "error").with("error")
					.put("message", e.getMessage());
			response.entity(entity).status(Status.INTERNAL_SERVER_ERROR);
		} catch (CORException e) {
			entity = request.getResponse().getErrors();
			response.entity(entity).status(422);
		}
		return response.build();
	}
}
