package prop.engine.modes;

import org.springframework.beans.factory.annotation.Autowired;

import prop.engine.middlewares.LoadOperationsStep;
import prop.engine.middlewares.PretendOperationsStep;

public class PretendModeResolver extends PropModeResolver {

	@Autowired
	public PretendModeResolver(LoadOperationsStep loadOperations,
			PretendOperationsStep pretendOperations) {
		use(loadOperations);
		use(pretendOperations);
	}

	@Override
	public String getMode() {
		return "pretend";
	}

}
