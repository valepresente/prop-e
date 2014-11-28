package prop.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.core.patterns.cor.CORException;
import prop.engine.modes.MapModeResolver;

@Service
public class PatchService {

	@Autowired
	private PropRegistry registry;

	@Autowired private MapModeResolver mapResolver;

	public void pretend(PatchMessage message) throws CORException {
		getMapResolver().process(message);
	}

	MapModeResolver getMapResolver() {
		return mapResolver;
	}

}
