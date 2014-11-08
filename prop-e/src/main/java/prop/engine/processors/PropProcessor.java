package prop.engine.processors;

import java.util.List;

import prop.engine.PropOperation;
import prop.engine.TriggeredPropOperation;

public interface PropProcessor {

	String getOperationType();

	List<TriggeredPropOperation> map(PropOperation operation);

}
