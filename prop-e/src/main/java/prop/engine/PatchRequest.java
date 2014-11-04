package prop.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class PatchRequest {

	private List<PropOperation> operations = new ArrayList<>();


	public PatchRequest(JsonNode operations) {
		loadOperations(operations);
	}

	private void loadOperations(JsonNode operations) {
		// TODO: load operations from JsonNode
	}

	public PatchResponse emptyResponse() {
		return new PatchResponse();
	}

	public List<PropOperation> getOperations() {
		return operations;
	}

}
