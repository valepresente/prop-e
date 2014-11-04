package prop.engine;

public class PatchMessage {

	private PatchRequest request;
	private PatchResponse response;

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

}
