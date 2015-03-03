package prop.engine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.engine.processors.PropProcessor;
import prop.engine.processors.PropProcessorObserver;
import prop.engine.processors.ResultStatus;
import prop.engine.processors.observers.ExecuteOperationsObserver;
import prop.engine.processors.observers.MapOtherOperationsObserver;

@Service
public class CancelOrderProcessor implements PropProcessor,
		MapOtherOperationsObserver, ExecuteOperationsObserver {

	@Override
	public String getOperationType() {
		return "cancelOrder";
	}

	@Override
	public PropOperation buildOperation(ObjectNode operation, ObjectNode errors) {
		return new PropOperation(operation);
	}

	@Override
	public PropProcessorObserver getObserver() {
		return this;
	}

	@Override
	public List<TriggeredPropOperation> map(PatchMessage message,
			PropOperation operation) {
		return null;
	}

	@Override
	public ResultStatus execute(PatchMessage message, PropOperation operation) {
		JsonNode op = operation.getParam("orderId");
		if (op == null || op.isNull() || op.asText().isEmpty()) {
			operation.getRealized().put("error_message", "unknown_order");
			return null;
		} else if (op.asText().equals("1")) {
			operation.getRealized().put("cancelation", "ok");
		} else if (op.asText().equals("2")) {
			operation.getRealized().put("cancelation", "not_possible");
			return ResultStatus.FAILED;
		}
		return ResultStatus.SUCCESS;
	}

}
