package prop.engine.processors.observers;

import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.processors.PropProcessorObserver;
import prop.engine.processors.ResultStatus;

public interface PretendOperationsObserver extends PropProcessorObserver {

	ResultStatus pretend(PatchMessage message, PropOperation operation);

}
