package prop.engine;

import java.util.ArrayList;
import java.util.List;

public class PatchRequest {

	private List<PropOperation> operations = new ArrayList<>();


	public PatchResponse emptyResponse() {
		return new PatchResponse();
	}

	public List<PropOperation> getOperations() {
		return operations;
	}

}
