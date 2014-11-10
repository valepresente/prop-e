package prop.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class PatchMessage {

	private PatchRequest request;
	private PatchResponse response;
	private List<PropOperation> effectiveOperations = new ArrayList<>();
	private JsonNode rawMessage;
	private ArrayNode entityErrors;

	public PatchMessage(JsonNode rawMessage) {
		this.request = new PatchRequest();
		this.rawMessage = rawMessage;
		this.response = request.emptyResponse();
		this.entityErrors = new ObjectMapper().createArrayNode();
	}

	public PatchRequest getRequest() {
		return request;
	}

	public JsonNode getRawMessage() {
		return rawMessage;
	}

	public PatchResponse getResponse() {
		return response;
	}

	public List<PropOperation> getEffectiveOperations() {
		return effectiveOperations;
	}

	public ArrayNode getEntityErrors() {
		return entityErrors;
	}

}
