package prop.engine.middlewares;

import org.springframework.stereotype.Service;

import prop.core.patterns.cor.Chain;
import prop.core.patterns.cor.Middleware;
import prop.engine.PatchRequest;

@Service
public class MapEffectiveOperationsStep implements Middleware<PatchRequest> {

	@Override
	public void call(Chain<PatchRequest> chain) {
		// TODO: implementation
	}

}
