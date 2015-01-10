package jrack.api.rs.controllers;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import prop.engine.OperationsRegistry;

@Path("operations")
public class OperationsController extends PropController<OperationsRegistry> {

	@Autowired
	private OperationsRegistry registry;

	@Override
	protected OperationsRegistry getRegistry() {
		return registry;
	}

}
