package freemind.tools;

import freemind.modes.MindMapNode;

public class MindMapNodePair {
	MindMapNode first;

	MindMapNode second;

	public MindMapNodePair(MindMapNode first, MindMapNode second) {
		this.first = first;
		this.second = second;
	}

	public MindMapNode getCorresponding() {
		return first;
	}

	public MindMapNode getCloneNode() {
		return second;
	}
}