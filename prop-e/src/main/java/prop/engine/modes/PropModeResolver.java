package prop.engine.modes;

import prop.core.patterns.cor.ChainOfResponsibility;
import prop.engine.PatchMessage;

abstract public class PropModeResolver extends
		ChainOfResponsibility<PatchMessage> {

	abstract public String getMode();

}
