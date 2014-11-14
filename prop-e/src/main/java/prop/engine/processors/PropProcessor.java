package prop.engine.processors;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.engine.PropOperation;
import prop.engine.TriggeredPropOperation;

public interface PropProcessor {

	String getOperationType();

	List<TriggeredPropOperation> map(PropOperation operation);

	/**
	 * Build an PropOperation object if no errors were found.
	 *
	 * @param operation The input data
	 * @param errors The object to be filled with errors of input data
	 * @return A PropOperation object that handles the input data
	 */
	PropOperation buildOperation(ObjectNode operation, ObjectNode errors);

}
