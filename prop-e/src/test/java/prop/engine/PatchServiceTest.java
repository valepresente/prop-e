package prop.engine;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonassert.JsonAssert;

import prop.core.patterns.cor.CORException;
import prop.test.AbstractTestCase;

public class PatchServiceTest extends AbstractTestCase {

	@Autowired
	private PatchService service;

	@Test
	public void testPatchServicePresence() {
		assertNotNull(service);
	}

	@Test
	public void testPretendCancelOrder() throws Exception {
		JsonNode cancelOrderData = new ObjectMapper().readTree(this.getClass().getClassLoader()
				.getResourceAsStream("prop/engine/fixtures/PatchService-pretendCancelOrder.json"));
		PatchMessage message = new PatchMessage(cancelOrderData);
		assertEquals(0, message.getEffectiveOperations().size());
		service.pretend(message);
		assertEquals(1, message.getEffectiveOperations().size());
	}

	@Test
	public void testPretendWithEmptyJSON() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper().readTree("{}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			service.pretend(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "Missing operations");
		}
	}

	@Test
	public void testPretendWithInvalidOperationsType() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper().readTree("{\"operations\":{}}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			service.pretend(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "Invalid operations type");
		}
	}

}
