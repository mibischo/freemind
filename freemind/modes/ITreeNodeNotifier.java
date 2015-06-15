package freemind.modes;

public interface ITreeNodeNotifier {
	
	public void fireTreeNodesInserted();
	
	public void fireTreeNodesRemoved();
	
	public void fireTreeStructureChanged();
	
	public void fireTreeNodesChanged();
	
	public TreeNodeNotifier setData(TreeNodeNotifierData data);
}
