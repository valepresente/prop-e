package prop.engine;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class PatchMessage {

	private PropRegistry registry;
	private PatchRequest request;
	private PatchResponse response;
	private List<PropOperation> effectiveOperations = new ArrayList<>();
	private JsonNode rawMessage;
	private String resourceType;

	public PatchMessage(PropRegistry registry, JsonNode rawMessage) {
		this.registry = registry;
		this.request = new PatchRequest();
		this.rawMessage = rawMessage;
		this.response = request.emptyResponse();
		String resourceType = rawMessage.path("resourceType").asText();
		if (resourceType == null || resourceType.isEmpty()) {
			resourceType = "operations";
		}
		this.resourceType = resourceType;
	}

	public PropRegistry getRegistry() {
		return registry;
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

	public String getResourceType() {
		return resourceType;
	}

}
