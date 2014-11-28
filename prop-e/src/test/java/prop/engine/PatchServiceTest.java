package prop.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import prop.engine.modes.MapModeResolver;
import prop.engine.modes.PropModeResolver;
import prop.test.AbstractTestCase;

public class PatchServiceTest extends AbstractTestCase {

	@Autowired
	private PatchService service;

	@Test
	public void testPatchServicePresence() {
		assertNotNull(service);
	}

	@Test
	public void testMapModeResolverSupported() {
		PropModeResolver resolver = service.getResolver("map");
		assertNotNull(resolver);
		assertThat(resolver, instanceOf(MapModeResolver.class));
	}
}
