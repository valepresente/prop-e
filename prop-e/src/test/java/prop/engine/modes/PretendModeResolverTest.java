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

	@Test
	public void testInvalidOperation() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper()
				.readTree("{\"operations\":[{}]}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			pretendResolver.process(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "invalid")
					.assertNotNull("$.attrs")
					.assertNotNull("$.attrs[0]")
					.assertNotNull("$.attrs[0].attrs")
					.assertNotNull("$.attrs[0].attrs.operationType")
					.assertEquals("$.attrs[0].attrs.operationType", "required")
					.assertNotNull("$.attrs[0].attrs.params")
					.assertEquals("$.attrs[0].attrs.params", "required");
		}
	}

	@Test
	public void testInvalidOperationProperties() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper()
				.readTree("{\"operations\":[{\"operationType\": \"\", \"params\":{}}]}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			pretendResolver.process(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "invalid")
					.assertNotNull("$.attrs")
					.assertNotNull("$.attrs[0]")
					.assertNotDefined("$.attrs[0].message")
					.assertNotNull("$.attrs[0].attrs")
					.assertNotNull("$.attrs[0].attrs.operationType")
					.assertEquals("$.attrs[0].attrs.operationType", "invalid")
					.assertNotNull("$.attrs[0].attrs.params")
					.assertEquals("$.attrs[0].attrs.params", "empty");
		}
	}

	@Test
	public void testUnknownOperation() throws Exception {
		JsonNode emptyOrderData = new ObjectMapper()
				.readTree("{\"operations\":[{\"operationType\": \"strangeOperation\", \"params\":{\"id\":1}}]}");
		PatchMessage message = new PatchMessage(emptyOrderData);
		try {
			pretendResolver.process(message);
			assertTrue("Not reachable code", false);
		} catch (CORException e) {
			JsonAssert.with(message.getResponse().getErrors().toString())
					.assertNotNull("$.message")
					.assertEquals("$.message", "invalid")
					.assertNotNull("$.attrs")
					.assertNotNull("$.attrs[0]")
					.assertNotDefined("$.attrs[0].message")
					.assertNotNull("$.attrs[0].attrs")
					.assertNotNull("$.attrs[0].attrs.operationType")
					.assertEquals("$.attrs[0].attrs.operationType", "unknown")
					.assertNotDefined("$.attrs[0].attrs.params");
		}
	}

}
