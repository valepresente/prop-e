package jrack.api.rs.controllers;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import prop.engine.OrderRegistry;

@Path("order")
public class OrderController extends PropController<OrderRegistry> {

	private enum SuportedOperation {
		cancel("cancelOrder");

		private String op;
		SuportedOperation(String op) {
			this.op = op;
		}
		public String getOp() {
			return op;
		}
	}

	@Autowired
	private OrderRegistry registry;

	@Override
	protected OrderRegistry getRegistry() {
		return registry;
	}

	@Override
	protected JsonNode buildOperation(String operation, String id) {
		try {
			SuportedOperation op = SuportedOperation.valueOf(operation);
			ObjectNode operationNode = new ObjectMapper().createObjectNode();
			operationNode.put("operationType", op.getOp());
			operationNode.with("params").put("orderId", id);
			return operationNode;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	protected String getResourceType() {
		return "order_operations";
	}

}
