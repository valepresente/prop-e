package prop.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.modes.MapModeResolver;

@Service
public class OperationsRegistry extends PropRegistry {

	@Autowired
	public OperationsRegistry(MapModeResolver mapResolver){
		register(mapResolver);
	}
}
