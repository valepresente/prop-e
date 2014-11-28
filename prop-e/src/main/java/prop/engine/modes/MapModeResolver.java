package prop.engine.modes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.PropRegistry;
import prop.engine.middlewares.LoadOperationsStep;
import prop.engine.middlewares.MapEffectiveOperationsStep;

@Service
public class MapModeResolver extends PropModeResolver {

	@Autowired
	public MapModeResolver(LoadOperationsStep loadOperations,
			MapEffectiveOperationsStep mapEffectiveOperations) {
		use(loadOperations);
		use(mapEffectiveOperations);
	}

	@Override
	public String getMode() {
		return "map";
	}

	@Autowired
	public void register(PropRegistry registry) {
		registry.register(this);
	}
}
