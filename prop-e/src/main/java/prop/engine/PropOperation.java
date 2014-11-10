package prop.engine;

public class PropOperation {

	private String type;

	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}

	public boolean isLike(PropOperation op) {
		// TODO: use params hash to compare
		return getType().equals(op.getType());
	}

}
