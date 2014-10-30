package prop.engine.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.PropRegistry;

@Service
public class CancelOrderProcessor implements PropProcessor {

	@Autowired
	public CancelOrderProcessor(PropRegistry registry) {
		registry.register(this);
	}

	@Override
	public String getOperationType() {
		return "cancelOrder";
	}

}
