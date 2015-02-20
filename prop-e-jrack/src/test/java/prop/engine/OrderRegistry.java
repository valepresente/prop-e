package prop.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.modes.MapModeResolver;

@Service
public class OrderRegistry extends PropRegistry {

	@Autowired
	public OrderRegistry(MapModeResolver mapResolver){
		register(mapResolver);
	}

	@Autowired
	public void setProcessors(CancelOrderProcessor cancelOrder) {
		register(cancelOrder);
	}
}
