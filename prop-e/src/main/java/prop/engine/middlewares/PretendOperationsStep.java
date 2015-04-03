package prop.engine.middlewares;

import java.util.List;

import org.springframework.stereotype.Service;

import prop.core.patterns.cor.CORException;
import prop.core.patterns.cor.Chain;
import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.PropRegistry;
import prop.engine.modes.PropResolverMiddleware;
import prop.engine.processors.PropProcessor;
import prop.engine.processors.PropProcessorObserver;
import prop.engine.processors.ResultStatus;
import prop.engine.processors.observers.PretendOperationsObserver;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class PretendOperationsStep implements PropResolverMiddleware {

	@Override
	public void call(Chain<PatchMessage> chain) throws CORException {
		PatchMessage message = chain.getRequestObject();
		List<PropOperation> operations = message.getRequest().getOperations();
		PropRegistry registry = message.getRegistry();
		int op = -1;
		for (PropOperation operation : operations) {
			op += 1;
			PropProcessor processor = registry
					.getProcessor(operation.getType());
			PropProcessorObserver observer = processor.getObserver();
			ResultStatus result = null;
			if (observer == null
					|| !(observer instanceof PretendOperationsObserver)) {
				result = ResultStatus.UNRESOLVED;
			} else {
				PretendOperationsObserver pretender = (PretendOperationsObserver) observer;
				result = pretender.pretend(message, operation);
				if (result == null) {
					result = ResultStatus.UNKNOWN;
				} else if (result == ResultStatus.ABORTED) {
					message.getResponse().throwError(
							String.format("error status %s (execution %d)",
									result, op));
				}
			}
			((ObjectNode) operation.getJson()).put("status", result.name());
		}
		chain.next();
	}

}
