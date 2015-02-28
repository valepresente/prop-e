package prop.engine.processors.observers;

import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.processors.PropProcessorObserver;
import prop.engine.processors.ResultStatus;

public interface ExecuteOperationsObserver extends PropProcessorObserver {

	ResultStatus execute(PatchMessage message, PropOperation operation);

}
