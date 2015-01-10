package prop.engine;

import org.springframework.beans.factory.FactoryBean;

public class PropRegistryBeanFactory implements FactoryBean<PropRegistry> {


	@Override
	public PropRegistry getObject() throws Exception {
		PropRegistry registry = new PropRegistry();
		return registry;
	}

	@Override
	public Class<?> getObjectType() {
		return PropRegistry.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
