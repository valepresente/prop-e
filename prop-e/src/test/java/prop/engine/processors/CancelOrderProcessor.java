package prop.engine.processors;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.engine.PropOperation;
import prop.engine.TriggeredPropOperation;
import prop.engine.processors.observers.MapOtherOperationsObserver;

@Service
public class CancelOrderProcessor implements PropProcessor, MapOtherOperationsObserver {

	@Override
	public String getOperationType() {
		return "cancelOrder";
	}

	@Override
	public List<TriggeredPropOperation> map(PropOperation operation) {
		return null;
	}

	@Override
	public PropOperation buildOperation(ObjectNode operation, ObjectNode errors) {
		PropOperation op = new PropOperation();
		op.setType(getOperationType());
		// TODO: load operation params
		return op;
	}

	@Override
	public PropProcessorObserver getObserver() {
		return this;
	}

}
