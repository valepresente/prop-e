package prop.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PatchMessage {

	private PropRegistry registry;
	private PatchRequest request;
	private PatchResponse response;
	private List<PropOperation> effectiveOperations = new ArrayList<>();
	private JsonNode rawMessage;
	private String resourceType;
	private Map<String, Object> context;

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
		context = new HashMap<>();
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

	public JsonNode getEffectiveMessage() {
		ObjectNode json = new ObjectMapper().createObjectNode();
		if (!json.has("_links")) {
			json.put("_links", rawMessage.get("_links"));
		}
		ArrayNode operations = json.withArray(resourceType);
		for (PropOperation op : effectiveOperations) {
			operations.add(op.getJson());
		}
		return json;
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

	public Object get(String contextObjectName) {
		return context.get(contextObjectName);
	}

	public void put(String contextObjectName, Object value) {
		context.put(contextObjectName, value);
	}

}
