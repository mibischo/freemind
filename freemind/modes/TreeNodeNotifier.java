package freemind.modes;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

public class TreeNodeNotifier implements ITreeNodeNotifier {

	
	private EventListenerList listenerList;
	private TreeNodeNotifierData data = new TreeNodeNotifierData();

	public TreeNodeNotifier(EventListenerList listenerList) {
		this.listenerList = listenerList;
	}
	
	public TreeNodeNotifier(Object source, Object[] path,
			int[] childIndices, Object[] children) {
		
		this.data.setSource(source);
		this.data.setPath(path);
		this.data.setChildIndices(childIndices);
		this.data.setChildren(children);
		
	}
	
	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node being changed
	 * @param path
	 *            the path to the root node
	 * @param childIndices
	 *            the indices of the changed elements
	 * @param children
	 *            the changed elements
	 * @see EventListenerList
	 */
	public void fireTreeNodesInserted() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		e = fireTreeNodesInserted(
				listeners, e);
		MindMapNode node = (MindMapNode) data.getPath()[data.getPath().length - 1];
		fireTreeNodesInserted(node
				.getListeners().getListenerList(), e);
	}

	private TreeModelEvent fireTreeNodesInserted(Object[] listeners,
			TreeModelEvent e) {
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(data.getSource(), data.getPath(), data.getChildIndices(), data.getChildren());
				((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
			}
		}
		return e;
	}

	public void fireTreeNodesRemoved() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		e = fireTreeNodesRemoved(
				listeners, e);
		MindMapNode node = (MindMapNode) data.getPath()[data.getPath().length - 1];
		fireTreeNodesRemoved(node
				.getListeners().getListenerList(), e);
	}

	private TreeModelEvent fireTreeNodesRemoved(Object[] listeners,
			TreeModelEvent e) {
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(data.getSource(), data.getPath(), data.getChildIndices(), data.getChildren());
				((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
			}
		}
		return e;
	}

	public void fireTreeStructureChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		e = fireTreeStructureChanged(
				listeners, e);
		MindMapNode node = (MindMapNode) data.getPath()[data.getPath().length - 1];
		fireTreeStructureChanged(node
				.getListeners().getListenerList(), e);
	}

	private TreeModelEvent fireTreeStructureChanged(
			Object[] listeners, TreeModelEvent e) {
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(data.getSource(), data.getPath(), data.getChildIndices(), data.getChildren());
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
		return e;
	}

	public void fireTreeNodesChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		e = fireTreeNodesChanged(data.getSource(), data.getPath(), data.getChildIndices(), data.getChildren(),
				listeners, e);
		MindMapNode node = (MindMapNode) data.getPath()[data.getPath().length - 1];
		fireTreeNodesChanged(data.getSource(), data.getPath(), data.getChildIndices(), data.getChildren(), node
				.getListeners().getListenerList(), e);
	}

	private TreeModelEvent fireTreeNodesChanged(Object source, Object[] path,
			int[] childIndices, Object[] children, Object[] listeners,
			TreeModelEvent e) {
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
		return e;
	}

	public TreeNodeNotifierData getData() {
		return data;
	}

	public TreeNodeNotifier setData(TreeNodeNotifierData data) {
		this.data = data;
		return this;
	}
	
	
}
