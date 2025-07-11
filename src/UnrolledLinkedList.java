/**
 * Represents an unrolled linked list that stores integers in fixed-size arrays.
 * Each node contains up to {@code arraySize} elements.
 * New nodes are added dynamically as needed.
 *
 * @author ujnaa
 */
public class UnrolledLinkedList {

    /**
     * Represents a node in the unrolled linked list.
     * Each node holds an array of integers and links to previous/next nodes.
     */
    private static class Node {
        /** array storing the elements of this node */
        private final int[] elements;
        /** number of used slots in {@link #elements} */
        private int count;
        /** previous node in the list */
        private Node prev;
        /** next node in the list */
        private Node next;

        /**
         * Creates a new node with an internal array of given size.
         *
         * @param size the size of the internal array
         */
        Node(int size) {
            this.elements = new int[size];
            this.count = 0;
        }
    }

    /** Default capacity used by the parameterless constructor. */
    private static final int DEFAULT_ARRAY_SIZE = 4;

    private final int arraySize;
    private int totalSize;
    private Node head;
    private Node tail;

    /**
     * Creates a new empty unrolled linked list using the default array size.
     */
    public UnrolledLinkedList() {
        this(DEFAULT_ARRAY_SIZE);
    }

    /**
     * Creates a new empty unrolled linked list with the given array size per node.
     *
     * @param arraySize maximum number of elements per node (must be > 0)
     */
    public UnrolledLinkedList(int arraySize) {
        assert arraySize > 0 : "arraySize must be > 0";
        this.arraySize = arraySize;
        this.totalSize = 0;
        this.head = new Node(arraySize);
        this.tail = this.head;
    }

    /**
     * Adds a new element at the end of the list.
     * Creates a new node if the current tail is full.
     *
     * @param element the element to add
     * @return {@code true} once the element has been inserted
     */
    public boolean push(int element) {
        if (tail.count == arraySize) {
            Node newNode = new Node(arraySize);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        tail.elements[tail.count++] = element;
        totalSize++;
        return true;
    }

    /**
     * Removes the last element in the list.
     * Deletes the node if it becomes empty and is not the only node.
     *
     * @return {@code true} if a node was removed, otherwise {@code false}
     */
    public boolean pop() {
        assert !isEmpty() : "cannot pop from empty list";
        tail.count--;
        totalSize--;

        if (tail.count == 0 && tail != head) {
            tail = tail.prev;
            tail.next = null;
            return true;
        }
        return false;
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param i index of the element (0-based, must be < size())
     * @return the element at index {@code i}
     */
    public int get(int i) {
        assert i >= 0 && i < totalSize : "index out of range";
        int index = i;
        Node currentNode = head;
        while (index >= currentNode.count) {
            index -= currentNode.count;
            currentNode = currentNode.next;
        }
        return currentNode.elements[index];
    }

    /**
     * Returns the total number of elements stored in the list.
     *
     * @return number of elements
     */
    public int size() {
        return totalSize;
    }

    /**
     * Checks whether the list contains no elements.
     *
     * @return {@code true} if the list is empty
     */
    public boolean isEmpty() {
        return totalSize == 0;
    }

    /**
     * Returns the number of array nodes currently in the list.
     *
     * @return number of nodes
     */
    public int amountArrays() {
        int count  = 0;
        Node currentNode = head;
        while (currentNode != null) {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    /**
     * Returns the number of elements stored in the node at the given index.
     *
     * @param index node index (0-based, must be < amountArrays())
     * @return number of elements in the specified node
     */
    public int sizeArray(int index) {
        Node currentNode = head;
        int i = index;
        while (i-- > 0) {
            currentNode = currentNode.next;
        }
        return currentNode.count;
    }

    /**
     * Returns a string containing all elements in order, separated by the given separator.
     *
     * @param separator the separator string (e.g., ", ")
     * @return string representation of the list
     */
    public String toString(String separator) {
        StringBuilder sb = new StringBuilder();
        Node current = head;

        while (current != null) {
            for (int i = 0; i < current.count; i++) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(current.elements[i]);
            }
            current = current.next;
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(", ");
    }
}
