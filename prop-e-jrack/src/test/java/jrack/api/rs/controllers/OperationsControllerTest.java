package jrack.api.rs.controllers;

import static com.jayway.jsonassert.JsonAssert.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import jrack.test.AbstractControllerTestCase;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OperationsControllerTest extends AbstractControllerTestCase<OperationsController> {

	@Test
	public void testEntrypoint() throws IOException {
		Response response = target("/operations").request().get();
		assertEquals(200, response.getStatus());
		assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

		with((InputStream) response.getEntity())
				.assertEquals("$._links.self.href", "/operations")
				.assertEquals("$._links.self.type", "application/hal+json")
				.assertEquals("$._links.map_operation_schema.href",
						"/operations/schemas/map")
				.assertEquals("$._links.map_operation_schema.type",
						"application/schema+json");
	}

	@Test
	public void testMapOperations() throws IOException {
		JsonNode json = new ObjectMapper()
		.readTree(this
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						"prop/engine/fixtures/MapModeResolver-cancelOrder.json"));
		Entity<JsonNode> entity = Entity.json(json);
		target("/operations").request().build("PATCH", entity).invoke();
	}
}