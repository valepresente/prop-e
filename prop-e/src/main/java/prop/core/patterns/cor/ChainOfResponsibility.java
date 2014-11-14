package prop.core.patterns.cor;

import java.util.ArrayList;
import java.util.List;

abstract public class ChainOfResponsibility<RequestObject> {

	private List<Middleware<RequestObject>> steps;

	public ChainOfResponsibility() {
		steps = new ArrayList<Middleware<RequestObject>>();
	}

	public void use(Middleware<RequestObject> middleware) {
		if (middleware != null) {
			steps.add(middleware);
		}
	}

	public void process(RequestObject request) throws CORException {
		new Chain<RequestObject>(steps.iterator(), request).next();
	}
}
