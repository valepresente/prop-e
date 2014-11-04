package prop.engine.processors;

import java.util.List;

import prop.engine.PropOperation;

public interface PropProcessor {

	String getOperationType();

	List<PropOperation> map(PropOperation operation);

}
