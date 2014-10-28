package prop.engine;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import prop.test.AbstractTestCase;

public class PatchServiceTest extends AbstractTestCase {

	@Autowired
	private PatchService service;

	@Test
	public void testPatchServicePresence() {
		assertNotNull(service);
	}
}
