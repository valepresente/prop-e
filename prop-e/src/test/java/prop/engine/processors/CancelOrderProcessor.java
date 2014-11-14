package prop.engine.processors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.engine.PropOperation;
import prop.engine.PropRegistry;
import prop.engine.TriggeredPropOperation;

@Service
public class CancelOrderProcessor implements PropProcessor {

	@Autowired
	public CancelOrderProcessor(PropRegistry registry) {
		registry.register(this);
	}

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

}
