package prop.engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class PropOperation {

	private ObjectNode operation;

	public PropOperation() {
		operation = new ObjectMapper().createObjectNode();
	}

	public PropOperation(ObjectNode operation) {
		this.operation = operation;
	}

	public PropOperation setType(String type) {
		operation.put("operationType", type);
		return this;
	}
	public String getType() {
		return operation.path("operationType").asText();
	}

	public boolean isLike(PropOperation op) {
		return getType().equals(op.getType()) && operation.equals(op.operation);
	}

	public JsonNode getParam(String fieldName) {
		return operation.path("params").path(fieldName);
	}

	public ObjectNode getParams() {
		return operation.with("params");
	}

	public ObjectNode getData() {
		return operation.with("data");
	}

	public ObjectNode getRealized() {
		return operation.with("realized");
	}

	public JsonNode getJson() {
		return operation;
	}

}
