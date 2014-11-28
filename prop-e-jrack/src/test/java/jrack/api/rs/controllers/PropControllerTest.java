package jrack.api.rs.controllers;

import static com.jayway.jsonassert.JsonAssert.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import jrack.test.AbstractControllerTestCase;

import org.junit.Test;

public class PropControllerTest extends AbstractControllerTestCase<PropController> {

	@Test
	public void testEntrypoint() throws IOException {
		Response response = target("/operations").request().get();
		assertEquals(200, response.getStatus());
		assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

		with((InputStream) response.getEntity()).assertEquals(
				"$._links.self.href", "/operations");
	}

}
