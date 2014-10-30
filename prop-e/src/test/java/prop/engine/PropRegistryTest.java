package prop.engine;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import prop.engine.processors.CancelOrderProcessor;
import prop.engine.processors.PropProcessor;
import prop.test.AbstractTestCase;

public class PropRegistryTest extends AbstractTestCase {

	@Autowired
	private PropRegistry registry;

	@Test
	public void testRegistry() {
		PropProcessor processor = registry.getProcessor("cancelOrder");
		Assert.assertNotNull(processor);
		Assert.assertTrue(processor instanceof CancelOrderProcessor);
	}

}
