package prop.engine.middlewares;

import org.springframework.stereotype.Service;

import prop.core.patterns.cor.Chain;
import prop.core.patterns.cor.Middleware;
import prop.engine.PatchMessage;

@Service
public class MapEffectiveOperationsStep implements Middleware<PatchMessage> {

	@Override
	public void call(Chain<PatchMessage> chain) {
		// TODO: implementation
	}

}
