package prop.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.modes.PropModeResolver;

@Service
public class PatchService {

	@Autowired
	private PropRegistry registry;

	public PropModeResolver getResolver(String mode) {
		return registry.getResolver(mode);
	}
}
