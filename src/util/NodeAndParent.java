package util;

public class NodeAndParent<T> {

    Node<T> targetNode;
    Node<T> parentNode;

    public NodeAndParent() {
    }

    public NodeAndParent(Node<T> targetNode, Node<T> parentNode) {
        this.targetNode = targetNode;
        this.parentNode = parentNode;
    }

    public Node<T> getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node<T> targetNode) {
        this.targetNode = targetNode;
    }

    public Node<T> getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node<T> parentNode) {
        this.parentNode = parentNode;
    }
}


