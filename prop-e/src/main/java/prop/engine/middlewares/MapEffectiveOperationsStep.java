package prop.engine.middlewares;

import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prop.core.patterns.cor.Chain;
import prop.core.patterns.cor.Middleware;
import prop.engine.PatchMessage;
import prop.engine.PropOperation;
import prop.engine.PropRegistry;
import prop.engine.TriggeredPropOperation;
import prop.engine.processors.PropProcessor;

@Service
public class MapEffectiveOperationsStep implements Middleware<PatchMessage> {

	@Autowired
	private PropRegistry registry;

	@Override
	public void call(Chain<PatchMessage> chain) {
		PatchMessage message = chain.getRequestObject();
		List<PropOperation> operations = message.getRequest().getOperations();
		List<PropOperation> effectiveOperations = message.getEffectiveOperations();
		for (PropOperation op : operations) {
			mapEffectiveOperations(op, effectiveOperations);
		}
		chain.next();
	}

	private void mapEffectiveOperations(PropOperation op,
			List<PropOperation> effectiveOperations) {
		if ((op instanceof TriggeredPropOperation)
				&& isOperationPresent(op, effectiveOperations)) {
			return;
		}
		effectiveOperations.add(op);
		Enumeration<PropProcessor> processors = registry.getProcessors();
		while (processors.hasMoreElements()) {
			PropProcessor processor = processors.nextElement();
			List<TriggeredPropOperation> moreOperations = processor.map(op);
			if (moreOperations != null && moreOperations.size() != 0) {
				for (PropOperation _op : moreOperations) {
					mapEffectiveOperations(_op, effectiveOperations);
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
