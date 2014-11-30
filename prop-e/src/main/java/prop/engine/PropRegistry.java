package prop.engine;

import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import prop.engine.modes.PropModeResolver;
import prop.engine.processors.PropProcessor;

@Service
public class PropRegistry {

	private ConcurrentHashMap<String, PropProcessor> registry;
	private ConcurrentHashMap<String, PropModeResolver> resolverRegistry;

	public PropRegistry() {
		registry = new ConcurrentHashMap<String, PropProcessor>();
		resolverRegistry = new ConcurrentHashMap<>();
	}

	public PropProcessor getProcessor(String operationType) {
		return registry.get(operationType);
	}

	public PropModeResolver getResolver(String mode) {
		return resolverRegistry.get(mode);
	}

	public void register(PropProcessor processor) {
		if (processor != null)
			registry.put(processor.getOperationType(), processor);
	}

	public void register(PropModeResolver resolver) {
		if (resolver != null)
			resolverRegistry.put(resolver.getMode(), resolver);
	}

	public Enumeration<PropProcessor> getProcessors() {
		return registry.elements();
	}

	public Set<String> getSupportedModes() {
		return resolverRegistry.keySet();
	}
}
