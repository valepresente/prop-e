package prop.engine.processors.observers;

import java.util.List;

import prop.engine.PropOperation;
import prop.engine.TriggeredPropOperation;
import prop.engine.processors.PropProcessorObserver;


public interface MapOtherOperationsObserver extends PropProcessorObserver {

	List<TriggeredPropOperation> map(PropOperation operation);

}
