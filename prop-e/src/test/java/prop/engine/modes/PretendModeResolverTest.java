package prop.engine.modes;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonassert.JsonAssert;

import prop.engine.PatchMessage;
import prop.engine.PropRegistry;
import prop.engine.processors.CancelOrderProcessor;
import prop.test.AbstractTestCase;

public class PretendModeResolverTest extends AbstractTestCase {


	@Autowired
	private PretendModeResolver pretendResolver;

	@Autowired
	private CancelOrderProcessor cancelOrderProcessor;

	private PropRegistry registry() {
		PropRegistry registry = new PropRegistry();
		registry.register(pretendResolver);
		registry.register(cancelOrderProcessor);
		return registry;
	}

	@Test
	public void testCancelOrder() throws Exception {
		JsonNode cancelOrderData = new ObjectMapper()
				.readTree(this
						.getClass()
						.getClassLoader()
						.getResourceAsStream(
								"prop/engine/fixtures/MapModeResolver-cancelOrder.json"));
		PatchMessage message = new PatchMessage(registry(), cancelOrderData);
		pretendResolver.process(message);
		JsonAssert.with(message.getRawMessage().toString())
			.assertEquals("operations[0].operationType", "cancelOrder")
			.assertEquals("operations[0].realized.pretended", true);
	}

}
