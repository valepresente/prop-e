package prop.engine;

import prop.core.patterns.cor.CORException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PatchResponse {

	private ObjectNode errors;

	public PatchResponse() {
		this.errors = new ObjectMapper().createObjectNode();
	}

	public ObjectNode getErrors() {
		return errors;
	}

	public ObjectNode newEntityError() {
		return getErrors().withArray("attrs").addObject();
	}

	public void throwError(String errorMessage) throws CORException {
		getErrors().put("message", errorMessage);
		throw new CORException(errorMessage);
	}

}
