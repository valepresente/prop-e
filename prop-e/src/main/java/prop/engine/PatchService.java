package prop.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.modes.PretendModeResolver;

@Service
public class PatchService {

	@Autowired
	private PropRegistry registry;

	@Autowired private PretendModeResolver pretendResolver;

	public void pretend(PatchRequest request) {
		getPretendResolver().process(request);
	}

	PretendModeResolver getPretendResolver() {
		return pretendResolver;
	}

}
