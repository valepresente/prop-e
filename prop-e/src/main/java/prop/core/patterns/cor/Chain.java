package prop.core.patterns.cor;

import java.util.Iterator;

public class Chain<RequestObject> {

	private Iterator<Middleware<RequestObject>> steps;
	private RequestObject request;

	public Chain(Iterator<Middleware<RequestObject>> iterator,
			RequestObject request) {
		this.steps = iterator;
		this.request = request;
	}

	public void next() {
		if (steps.hasNext()) {
			steps.next().call(this);
		}
	}

	public RequestObject getRequestObject() {
		return request;
	}

}
