package prop.engine.modes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import prop.core.patterns.cor.CORException;
import prop.engine.PatchMessage;
import prop.test.AbstractTestCase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonassert.JsonAssert;

public class PretendModeResolverTest extends AbstractTestCase {

	@Autowired
	private PretendModeResolver pretendResolver;

	@Test
	public void testCancelOrder() throws Exception {
		JsonNode cancelOrderData = new ObjectMapper()
				.readTree(this
						.getClass()
						.getClassLoader()
						.getResourceAsStream(
								"prop/engine/fixtures/PretendModeResolver-cancelOrder.json"));
		PatchMessage message = new PatchMessage(cancelOrderData);
		assertEquals(0, message.getEffectiveOperations().size());
		pretendResolver.process(message);
		assertEquals(1, message.getEffectiveOperations().size());
	}

	@Test
	public void testWithEmptyJSON() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper().readTree("{}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			pretendResolver.process(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "Missing operations");
		}
	}

	@Test
	public void testWithInvalidOperationsType() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper()
				.readTree("{\"operations\":{}}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			pretendResolver.process(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "Invalid operations type");
		}
	}

	@Test
	public void testEmptyOperations() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper()
				.readTree("{\"operations\":[]}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			pretendResolver.process(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "No operations");
		}
	}

}
