package prop.engine.middlewares;

import java.util.Enumeration;
import java.util.List;

import org.springframework.stereotype.Service;

import prop.core.patterns.cor.CORException;
import prop.core.patterns.cor.Chain;
import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.PropRegistry;
import prop.engine.TriggeredPropOperation;
import prop.engine.modes.PropResolverMiddleware;
import prop.engine.processors.PropProcessor;
import prop.engine.processors.PropProcessorObserver;
import prop.engine.processors.observers.MapOtherOperationsObserver;

@Service
public class MapEffectiveOperationsStep implements PropResolverMiddleware {

	@Override
	public void call(Chain<PatchMessage> chain) throws CORException {
		PatchMessage message = chain.getRequestObject();
		PropRegistry registry = message.getRegistry();
		List<PropOperation> operations = message.getRequest().getOperations();
		List<PropOperation> effectiveOperations = message.getEffectiveOperations();
		for (PropOperation op : operations) {
			mapEffectiveOperations(registry, op, effectiveOperations);
		}
		chain.next();
	}

	private void mapEffectiveOperations(PropRegistry registry, PropOperation op,
			List<PropOperation> effectiveOperations) {
		if ((op instanceof TriggeredPropOperation)
				&& isOperationPresent(op, effectiveOperations)) {
			return;
		}
		effectiveOperations.add(op);
		Enumeration<PropProcessor> processors = registry.getProcessors();
		while (processors.hasMoreElements()) {
			PropProcessor processor = processors.nextElement();
			PropProcessorObserver observer = processor.getObserver();
			if (observer == null
					|| !(observer instanceof MapOtherOperationsObserver))
				continue;
			MapOtherOperationsObserver mapper = (MapOtherOperationsObserver) observer;
			List<TriggeredPropOperation> moreOperations = mapper.map(op);
			if (moreOperations != null && moreOperations.size() != 0) {
				for (PropOperation _op : moreOperations) {
					mapEffectiveOperations(registry, _op, effectiveOperations);
				}
			}
		}
	}

	private boolean isOperationPresent(PropOperation op,
			List<PropOperation> operations) {
		for (PropOperation _op : operations) {
			if (_op.isLike(op)) return true;
		}
		return false;
	}

}
