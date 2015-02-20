package prop.engine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.engine.processors.PropProcessor;
import prop.engine.processors.PropProcessorObserver;
import prop.engine.processors.observers.MapOtherOperationsObserver;

@Service
public class CancelOrderProcessor implements PropProcessor, MapOtherOperationsObserver {

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
		return null;
	}

	@Override
	public List<TriggeredPropOperation> map(PatchMessage message,
			PropOperation operation) {
		return null;
	}

}
