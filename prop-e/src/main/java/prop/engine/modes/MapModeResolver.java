package prop.engine.modes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.core.patterns.cor.ChainOfResponsibility;
import prop.engine.PatchMessage;
import prop.engine.middlewares.LoadOperationsStep;
import prop.engine.middlewares.MapEffectiveOperationsStep;

@Service
public class MapModeResolver extends ChainOfResponsibility<PatchMessage> {

	@Autowired
	public MapModeResolver(LoadOperationsStep loadOperations,
			MapEffectiveOperationsStep mapEffectiveOperations) {
		use(loadOperations);
		use(mapEffectiveOperations);
	}

}
