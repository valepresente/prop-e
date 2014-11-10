package prop.engine.middlewares;

import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.core.patterns.cor.Chain;
import prop.core.patterns.cor.Middleware;
import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.PropRegistry;
import prop.engine.processors.PropProcessor;

@Service
public class LoadOperationsStep implements Middleware<PatchMessage> {

	@Autowired
	private PropRegistry registry;

	@Override
	public void call(Chain<PatchMessage> chain) {
		PatchMessage message = chain.getRequestObject();
		if (hasValidNode(message) && loadAllOperations(message)) {
			chain.next();
		}
	}

	private boolean hasValidNode(PatchMessage message) {
		JsonNode json = message.getRawMessage();
		if (!json.hasNonNull("operations")) {
			// TODO: set the response with error
			return false;
		}
		JsonNode node = json.get("operations");
		if (!node.isArray()) {
			// TODO: set the response with error
			return false;
		}
		return true;
	}

	private boolean loadAllOperations(PatchMessage message) {
		List<PropOperation> operations = message.getRequest().getOperations();
		ArrayNode list = (ArrayNode) message.getRawMessage().get("operations");
		message.getEntityErrors().removeAll();
		PropOperation op;
		boolean result = true;
		for (int n = 0, len = list.size(); n < len; n++) {
			JsonNode jsonOp = list.get(n);
			ObjectNode jsonErr = message.getEntityErrors().addObject();
			if (isOperationStructure(jsonOp, jsonErr) &&
					(op = buildOperation(jsonOp, jsonErr)) != null) {
				operations.add(op);
			} else {
				result = false;
			}
		}
		return result;
	}

	private boolean isOperationStructure(JsonNode op, ObjectNode jsonErr) {
		if (op == null || !op.isObject()) {
			jsonErr.put("message", "invalid");
			return false;
		}
		JsonNode node;
		node = op.get("operationType");
		if (node == null || !node.isTextual() || node.textValue().length() == 0) {
			jsonErr.put("message", "invalid operationType");
			return false;
		}
		node = op.get("params");
		if (node == null || !node.isObject() || node.size() == 0) {
			jsonErr.put("message", "invalid params");
			return false;
		}
		return true;
	}

	private PropOperation buildOperation(JsonNode jsonOp, ObjectNode jsonErr) {
		Enumeration<PropProcessor> processors = registry.getProcessors();
		PropOperation op;
		String operationType = jsonOp.get("operationType").asText();
		while (processors.hasMoreElements()) {
			PropProcessor processor = processors.nextElement();
			if (operationType.equals(processor.getOperationType())) {
				op = processor.buildOperation((ObjectNode) jsonOp);
				if (op != null) return op;
			}
		}
		jsonErr.put("message", "unknown operationType");
		return null;
	}

}
