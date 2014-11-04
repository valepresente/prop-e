package prop.engine;

import java.util.ArrayList;
import java.util.List;

public class PatchMessage {

	private PatchRequest request;
	private PatchResponse response;
	private List<PropOperation> effectiveOperations = new ArrayList<>();

	public PatchMessage(PatchRequest request) {
		this.request = request;
		this.response = request.emptyResponse();
	}

	public PatchRequest getRequest() {
		return request;
	}

	public PatchResponse getResponse() {
		return response;
	}

	public List<PropOperation> getEffectiveOperations() {
		return effectiveOperations;
	}

}
