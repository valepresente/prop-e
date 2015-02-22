package jrack.api.rs.controllers;

import static com.jayway.jsonassert.JsonAssert.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jrack.test.AbstractControllerTestCase;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OrderControllerTest extends AbstractControllerTestCase<OrderController> {

	@Test
	public void testMapOperations() throws IOException {
		Response response = target("/order/1/to/cancel").request().get();
		assertEquals(200, response.getStatus());
		with((InputStream) response.getEntity())
			.assertEquals("resourceType", "order_operations")
			.assertEquals("order_operations[0].operationType", "cancelOrder");
	}

	@Test
	public void testMapUnknownOperations() throws IOException {
		Response response = target("/order/1/to/cancelAll").request().get();
		assertEquals(501, response.getStatus());
	}

	@Test
	public void testExecuteOperations() throws IOException {
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("resourceType", "order_operations");
		json.withArray("order_operations").addObject().with("params")
				.put("orderId", "1");
		Entity<ObjectNode> entity = Entity.entity(json,
				MediaType.APPLICATION_JSON);
		Response response = target("/order/1/to/cancel").request().method(
				"PATCH", entity);
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testExecuteOperationsWithEmptyBody() throws IOException {
		Response response = target("/order/1/to/cancel").request().method(
				"PATCH");
		assertEquals(400, response.getStatus());
	}
}
