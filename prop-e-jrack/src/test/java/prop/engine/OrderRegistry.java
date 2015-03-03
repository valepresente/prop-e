package prop.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.modes.ExecuteModeResolver;
import prop.engine.modes.MapModeResolver;

@Service
public class OrderRegistry extends PropRegistry {

	@Autowired
	public OrderRegistry(MapModeResolver mapper, ExecuteModeResolver executor) {
		register(mapper);
		register(executor);
	}

	@Autowired
	public void setProcessors(CancelOrderProcessor cancelOrder) {
		register(cancelOrder);
	}
}
