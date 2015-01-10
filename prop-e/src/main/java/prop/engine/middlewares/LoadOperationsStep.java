package prop.engine.middlewares;

import java.util.Enumeration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.core.patterns.cor.CORException;
import prop.core.patterns.cor.Chain;
import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.PropRegistry;
import prop.engine.modes.PropResolverMiddleware;
import prop.engine.processors.PropProcessor;

@Service
public class LoadOperationsStep implements PropResolverMiddleware {

	@Override
	public void call(Chain<PatchMessage> chain) throws CORException {
		PatchMessage message = chain.getRequestObject();
		validateStructure(message);
		loadAllOperations(message);
		chain.next();
	}

	private void validateStructure(PatchMessage message) throws CORException {
		JsonNode json = message.getRawMessage();
		if (!json.hasNonNull("operations")) {
			message.getResponse().throwError("Missing operations");
		}
		JsonNode node = json.get("operations");
		if (!node.isArray()) {
			message.getResponse().throwError("Invalid operations type");
		}
		if (node.size() == 0) {
			message.getResponse().throwError("No operations");
		}
	}

	private void loadAllOperations(PatchMessage message) throws CORException {
		List<PropOperation> operations = message.getRequest().getOperations();
		PropRegistry registry = message.getRegistry();
		ArrayNode list = (ArrayNode) message.getRawMessage().get("operations");
		PropOperation op;
		boolean errorsFound = false;
		for (int n = 0, len = list.size(); n < len; n++) {
			JsonNode jsonOp = list.get(n);
			ObjectNode jsonErr = message.getResponse().newEntityError();
			if (isOperationStructure(jsonOp, jsonErr) &&
					(op = buildOperation(registry, jsonOp, jsonErr)) != null) {
				operations.add(op);
			} else {
				errorsFound = true;
			}
		}
		if (errorsFound) {
			message.getResponse().throwError("invalid");
		}
	}

	private boolean isOperationStructure(JsonNode op, ObjectNode jsonErr) {
		if (op == null || !op.isObject()) {
			jsonErr.put("message", "invalid");
			return false;
		}
		JsonNode node;
		boolean isOk = true;
		node = op.get("operationType");
		if (node == null || node.isNull()) {
			jsonErr.with("attrs").put("operationType", "required");
			isOk = false;
		}
		else if (!node.isTextual() || node.textValue().length() == 0) {
			jsonErr.with("attrs").put("operationType", "invalid");
			isOk = false;
		}
		node = op.get("params");
		if (node == null || node.isNull()) {
			jsonErr.with("attrs").put("params", "required");
			isOk = false;
		}
		else if (!node.isObject()) {
			jsonErr.with("attrs").put("params", "invalid");
			isOk = false;
		}
		else if (node.size() == 0) {
			jsonErr.with("attrs").put("params", "empty");
			isOk = false;
		}
		return isOk;
	}

	private PropOperation buildOperation(PropRegistry registry,
			JsonNode jsonOp, ObjectNode jsonErr) {
		Enumeration<PropProcessor> processors = registry.getProcessors();
		PropOperation op;
		String operationType = jsonOp.get("operationType").asText();
		while (processors.hasMoreElements()) {
			PropProcessor processor = processors.nextElement();
			if (operationType.equals(processor.getOperationType())) {
				op = processor.buildOperation((ObjectNode) jsonOp, jsonErr);
				return op;
			}
		}
		jsonErr.with("attrs").put("operationType", "unknown");
		return null;
	}

}
