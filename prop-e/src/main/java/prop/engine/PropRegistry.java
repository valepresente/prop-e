package prop.engine;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import prop.engine.processors.PropProcessor;

@Service
public class PropRegistry {

	private ConcurrentHashMap<String, PropProcessor> registry;

	public PropRegistry() {
		registry = new ConcurrentHashMap<String, PropProcessor>();
	}

	public PropProcessor getProcessor(String operationType) {
		return registry.get(operationType);
	}

	public void register(PropProcessor processor) {
		if (processor != null)
			registry.put(processor.getOperationType(), processor);
	}

}
