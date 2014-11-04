package prop.engine.modes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.core.patterns.cor.ChainOfResponsibility;
import prop.engine.PatchMessage;
import prop.engine.middlewares.MapEffectiveOperationsStep;

@Service
public class PretendModeResolver extends ChainOfResponsibility<PatchMessage> {

	@Autowired
	public PretendModeResolver(MapEffectiveOperationsStep mapEffectiveOperations) {
		use(mapEffectiveOperations);
	}

}
