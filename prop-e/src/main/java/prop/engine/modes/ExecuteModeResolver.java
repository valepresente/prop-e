package prop.engine.modes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.engine.middlewares.ExecuteOperationsStep;
import prop.engine.middlewares.LoadOperationsStep;

@Service
public class ExecuteModeResolver extends PropModeResolver {

	@Autowired
	public ExecuteModeResolver(LoadOperationsStep loadOperations,
			ExecuteOperationsStep executeOperations) {
		use(loadOperations);
		use(executeOperations);
	}

	@Override
	public String getMode() {
		return "execute";
	}

}
