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
		Response response = target("/order/1/operations/to/cancel").request().get();
		assertEquals(200, response.getStatus());
		with((InputStream) response.getEntity())
			.assertEquals("resourceType", "order_operations")
			.assertEquals("order_operations[0].operationType", "cancelOrder");
	}

	@Test
	public void testMapUnknownOperations() throws IOException {
		Response response = target("/order/1/operations/to/cancelAll").request().get();
		assertEquals(404, response.getStatus());
	}

	@Test
	public void testExecuteOperationsWithSuccess() throws IOException {
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("resourceType", "order_operations");
		ObjectNode op = json.withArray("order_operations").addObject();
		op.put("operationType", "cancelOrder")
			.with("params").put("orderId", "1");
		Entity<ObjectNode> entity = Entity.entity(json,
				MediaType.APPLICATION_JSON);
		Response response = target("/order/1/operations/to/cancel").request().method(
				"PATCH", entity);
		assertEquals(200, response.getStatus());
		with((InputStream) response.getEntity())
			.assertEquals("resourceType", "order_operations")
			.assertEquals("order_operations[0].operationType", "cancelOrder")
			.assertEquals("order_operations[0].status", "SUCCESS")
			.assertEquals("order_operations[0].realized.cancelation", "ok")
		;
	}

	@Test
	public void testExecuteOperationsWithUnknownResolution() throws IOException {
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("resourceType", "order_operations");
		ObjectNode op = json.withArray("order_operations").addObject();
		op.put("operationType", "cancelOrder")
			.with("params").put("orderId", (String)null);
		Entity<ObjectNode> entity = Entity.entity(json,
				MediaType.APPLICATION_JSON);
		Response response = target("/order/1/operations/to/cancel").request().method(
				"PATCH", entity);
		assertEquals(200, response.getStatus());
		with((InputStream) response.getEntity())
			.assertEquals("resourceType", "order_operations")
			.assertEquals("order_operations[0].operationType", "cancelOrder")
			.assertEquals("order_operations[0].status", "UNKNOWN")
			.assertEquals("order_operations[0].realized.error_message", "unknown_order")
		;
	}

	@Test
	public void testExecuteOperationsWithFail() throws IOException {
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("resourceType", "order_operations");
		ObjectNode op = json.withArray("order_operations").addObject();
		op.put("operationType", "cancelOrder")
			.with("params").put("orderId", "2");
		Entity<ObjectNode> entity = Entity.entity(json,
				MediaType.APPLICATION_JSON);
		Response response = target("/order/1/operations/to/cancel").request().method(
				"PATCH", entity);
		assertEquals(200, response.getStatus());
		with((InputStream) response.getEntity())
			.assertEquals("resourceType", "order_operations")
			.assertEquals("order_operations[0].operationType", "cancelOrder")
			.assertEquals("order_operations[0].status", "FAILED")
			.assertEquals("order_operations[0].realized.cancelation", "not_possible")
		;
	}

	@Test
	public void testExecuteOperationsWithEmptyBody() throws IOException {
		Response response = target("/order/1/operations/to/cancel").request()
				.method("PATCH");
		assertEquals(400, response.getStatus());
		with((InputStream) response.getEntity()).assertEquals("resourceType",
				"error").assertEquals("error.message", "Empty body");
	}

	@Test
	public void testExecuteOperationsWithBadRequestedJson() throws IOException {
		String json = "{\n\t\"resourceType\":\"order_operations\",}";
		Entity<String> entity = Entity.entity(json,
				MediaType.APPLICATION_JSON);
		Response response = target("/order/1/operations/to/cancel").request().method(
				"PATCH", entity);
		assertEquals(400, response.getStatus());
		with((InputStream) response.getEntity())
				.assertEquals("resourceType", "error")
				.assertEquals(
						"error.message",
						"Unexpected character ('}' (code 125)): was expecting double-quote to start field name");
	}
}
