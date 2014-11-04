package prop.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class PatchRequest {

	private List<PropOperation> operations = new ArrayList<>();


	public PatchRequest(JsonNode operations) {
		loadOperations(operations);
	}

	private void loadOperations(JsonNode operations) {
		if (!operations.hasNonNull("operations")) return;
		JsonNode node = operations.get("operations");
		if (!node.isArray()) return;
		ArrayNode list = (ArrayNode) node;
		for (int n = 0, len = list.size(); n < len; n++) {
			loadOperation(list.get(n));
		}
	}

	private void loadOperation(JsonNode jsonNode) {
		PropOperation operation = new PropOperation();
		operation.setType(jsonNode.get("operationType").asText());
		// TODO: load params
		operations.add(operation);
	}

	public PatchResponse emptyResponse() {
		return new PatchResponse();
	}

	public List<PropOperation> getOperations() {
		return operations;
	}

}
