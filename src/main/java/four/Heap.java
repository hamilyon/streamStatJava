package four;

import java.util.Comparator;
import java.util.List;

public class Heap {

    private static final int DEFAULT_CAPACITY = 13;
    private Node[] elements;
    private int size;
    private Comparator<Node> comparator;

    public Heap() {
        super();
        elements = new Node[DEFAULT_CAPACITY];
        comparator = new Comparator<Node>() {
            @Override
            public int compare(Node first, Node second) {
                return first.count - second.count;
            }
        };
    }

    public Object get() {
        if (isEmpty()) {
            throw new RuntimeException();
        } else {
            return elements[1];
        }
    }

    public int add(Node element) {
        if (isAtCapacity()) {
            grow();
        }
        elements[++size] = element;
        int position = percolateUpMinHeapReturningIndex(size);
        element.position = position;
        return position;
    }

    public Node max() {
        if (isEmpty()) {
            throw new RuntimeException();
        } else {
            return elements[1];
        }
    }

    private boolean isAtCapacity() {
        return elements.length == size + 1;
    }


    private int percolateUpMinHeapReturningIndex(Integer startIndex) {
        int hole = startIndex;
        Node element = elements[hole];

        while (hole > 1 && compare(element, elements[hole / 2]) > 0) {
            final int next = hole / 2;
            elements[hole] = elements[next];
            hole = next;
        }

        elements[hole] = element;
        return hole;
    }

    private int percolateDownMaxHeapReturningIndex(final int index) {
        final Node element = elements[index];
        int hole = index;

        while ((hole * 2) <= size) {
            int child = hole * 2;

            if (child != size && compare(elements[child + 1], elements[child]) > 0) {
                child++;
            }

            if (compare(elements[child], element) <= 0) {
                break;
            }

            elements[hole] = elements[child];
            hole = child;
        }

        elements[hole] = element;

        return hole;
    }

    public void updateDecrementedNodes(List<Node> toDecrement) {
        for (Node node : toDecrement) {
            node.count -= 1;
            node.position = percolateDownMaxHeapReturningIndex(node.position);
        }
    }

    public void incrementNode(Node node) {
        node.count += 1;
        if (node.position == null) {
            add(node);
        } else {
            node.position = percolateUpMinHeapReturningIndex(node.position);
        }
    }

    private int compare(Node a, Node b) {
        return comparator.compare(a, b);
    }

    private void grow() {
        final Node[] array = new Node[elements.length * 2];
        System.arraycopy(elements, 0, array, 0, elements.length);
        elements = array;
    }

    public boolean isEmpty() {
        return size == 0;
    }


}
