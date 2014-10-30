package prop.core.patterns.cor;

public interface Middleware<RequestObject> {

	void call(Chain<RequestObject> chain);

}
