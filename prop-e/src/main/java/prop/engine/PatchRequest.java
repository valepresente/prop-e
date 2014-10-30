package prop.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class PatchRequest {

	private List<PropOperation> operations = new ArrayList<>();
	private List<PropOperation> effectiveOperations = new ArrayList<>();

	private PatchResponse response = new PatchResponse();


	public PatchRequest(JsonNode operations) {
		loadOperations(operations);
	}

	private void loadOperations(JsonNode operations) {
		// TODO: load operations from JsonNode
	}

	public PatchResponse getResponse() {
		return response;
	}

	public List<PropOperation> getOperations() {
		return operations;
	}

	public List<PropOperation> getEffectiveOperations() {
		return effectiveOperations;
	}

}
