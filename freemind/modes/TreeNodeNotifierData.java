package freemind.modes;

public class TreeNodeNotifierData {
	private int[] childIndices;
	private Object[] path;
	private Object source;
	private Object[] children;

	public TreeNodeNotifierData() {
	}
	
	

	public TreeNodeNotifierData(Object source, Object[] path, int[] childIndices,
			Object[] children) {
		super();
		this.childIndices = childIndices;
		this.path = path;
		this.source = source;
		this.children = children;
	}



	public int[] getChildIndices() {
		return childIndices;
	}

	public void setChildIndices(int[] childIndices) {
		this.childIndices = childIndices;
	}

	public Object[] getPath() {
		return path;
	}

	public void setPath(Object[] path) {
		this.path = path;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public Object[] getChildren() {
		return children;
	}

	public void setChildren(Object[] children) {
		this.children = children;
	}
}