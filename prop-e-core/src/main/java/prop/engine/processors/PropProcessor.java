package prop.engine.processors;

import prop.engine.PropOperation;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface PropProcessor {

	String getOperationType();

	/**
	 * Build an PropOperation object if no errors were found.
	 *
	 * @param operation The input data
	 * @param errors The object to be filled with errors of input data
	 * @return A PropOperation object that handles the input data
	 */
	PropOperation buildOperation(ObjectNode operation, ObjectNode errors);

	PropProcessorObserver getObserver();
}
