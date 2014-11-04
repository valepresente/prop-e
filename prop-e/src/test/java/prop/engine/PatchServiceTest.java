package prop.engine;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import prop.test.AbstractTestCase;

public class PatchServiceTest extends AbstractTestCase {

	@Autowired
	private PatchService service;

	@Test
	public void testPatchServicePresence() {
		assertNotNull(service);
	}

	@Test
	public void testPretendCancelOrder() throws IOException {
		JsonNode cancelOrderData = new ObjectMapper().readTree(this.getClass().getClassLoader()
				.getResourceAsStream("prop/engine/fixtures/PatchService-pretendCancelOrder.json"));
		PatchRequest request = new PatchRequest(cancelOrderData);
		PatchMessage message = new PatchMessage(request);
		service.pretend(message);
		// TODO: more assertions required
	}

}
