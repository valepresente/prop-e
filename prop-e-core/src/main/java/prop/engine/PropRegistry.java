package prop.engine;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import prop.engine.modes.PropModeResolver;
import prop.engine.processors.PropProcessor;

public class PropRegistry {

	private ConcurrentHashMap<String, PropProcessor> registry;
	private ArrayList<String> registryKeys;
	private ConcurrentHashMap<String, PropModeResolver> resolverRegistry;

	public PropRegistry() {
		registry = new ConcurrentHashMap<String, PropProcessor>();
		registryKeys = new ArrayList<>();
		resolverRegistry = new ConcurrentHashMap<>();
	}

	public PropProcessor getProcessor(String operationType) {
		return registry.get(operationType);
	}

	public PropModeResolver getResolver(String mode) {
		return resolverRegistry.get(mode);
	}

	public void register(PropProcessor processor) {
		if (processor != null) {
			registryKeys.remove(processor.getOperationType());
			registryKeys.add(processor.getOperationType());
			registry.put(processor.getOperationType(), processor);
		}
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

	public Iterator<String> getProcessorTypes() {
		return registryKeys.iterator();
	}

}
