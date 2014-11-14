package prop.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.core.patterns.cor.CORException;
import prop.engine.modes.PretendModeResolver;

@Service
public class PatchService {

	@Autowired
	private PropRegistry registry;

	@Autowired private PretendModeResolver pretendResolver;

	public void pretend(PatchMessage message) throws CORException {
		getPretendResolver().process(message);
	}

	PretendModeResolver getPretendResolver() {
		return pretendResolver;
	}

}
