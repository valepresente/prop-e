package prop.engine.processors;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.TriggeredPropOperation;
import prop.engine.processors.observers.MapOtherOperationsObserver;
import prop.engine.processors.observers.PretendOperationsObserver;

@Service
public class CancelOrderProcessor implements PropProcessor,
		MapOtherOperationsObserver, PretendOperationsObserver {

	@Override
	public String getOperationType() {
		return "cancelOrder";
	}

	@Override
	public List<TriggeredPropOperation> map(PatchMessage message, PropOperation operation) {
		return null;
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
	public ResultStatus pretend(PatchMessage message, PropOperation operation) {
		operation.getRealized().put("pretended", true);
		return ResultStatus.SUCCESS;
	}

}
