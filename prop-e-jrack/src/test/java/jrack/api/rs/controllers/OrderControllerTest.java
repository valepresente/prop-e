package jrack.api.rs.controllers;

import static com.jayway.jsonassert.JsonAssert.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import jrack.test.AbstractControllerTestCase;

import org.junit.Test;

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
}
