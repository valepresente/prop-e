package prop.engine;

import org.springframework.stereotype.Service;

@Service
public class PatchService {

	public PatchResponse pretend(PatchRequest request) {
		PatchResponse response = request.buildResponse();
		// TODO: implementation
		return response;
	}

}
