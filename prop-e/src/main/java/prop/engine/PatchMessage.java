package prop.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class PatchMessage {

	private PatchRequest request;
	private PatchResponse response;
	private List<PropOperation> effectiveOperations = new ArrayList<>();
	private JsonNode rawMessage;

	public PatchMessage(JsonNode rawMessage) {
		this.request = new PatchRequest();
		this.rawMessage = rawMessage;
		this.response = request.emptyResponse();
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

}
