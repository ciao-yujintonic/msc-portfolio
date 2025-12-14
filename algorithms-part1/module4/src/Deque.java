import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private int size;
	private Node head;
	private Node tail;

	// construct an empty deque
	public Deque() {
		head = null;
		tail = null;
		size = 0;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return head == null;
	}

	// return the number of items on the deque
	public int size() {
		return size;
	}

	// add the item to the front
	public void addFirst(Item value) {
		if (value == null)
			throw new IllegalArgumentException();
		Node node = new Node(value);
		if (head == null) {
			head = node;
			tail = node;
		} else {
			node.next = head;
			head.prev = node;
			head = node;
		}
		size++;
	}

	// add the item to the last
	public void addLast(Item value) {
		if (value == null)
			throw new IllegalArgumentException();
		Node node = new Node(value);
		if (head == null) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (head == null)
			throw new java.util.NoSuchElementException();
		Item value = head.value;
		if (head.next != null) {
			head = head.next;
			head.prev = null;
		} else {
			head = null;
			tail = null;
		}
		size--;
		return value;
	}

    // remove and return the item from the back
	public Item removeLast() {
		if (tail == null)
			throw new java.util.NoSuchElementException();
		Item value = tail.value;
		if (tail.prev != null) {
			tail = tail.prev;
			tail.next = null;
		} else {
			tail = null;
			head = null;
		}
		size--;
		return value;
	}


	@Override
    // return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			private Node current = head;

			@Override
			public boolean hasNext() {
				return (current != null);
			}

			@Override
			public Item next() {
				if (current == null) throw new java.util.NoSuchElementException();
				Item item = current.value;
				current = (current.next == null) ? (null) : (current.next);
				return item;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	private class Node {
		private Node prev;
		private Item value;
		private Node next;

		Node(Item value) {
			this.prev = null;
			this.value = value;
			this.next = null;
		}
	}

    // unit testing (required)
	public static void main(String[] args) {
		Deque<String> deck = new Deque<>();
		deck.addFirst("AA");
		deck.addFirst("BB");
		deck.addLast("CC");
		deck.addLast("DD");
		System.out.println(deck.isEmpty());
		System.out.println(deck.size());
		System.out.println(deck.removeFirst());
		System.out.println(deck.removeLast());
		Iterator<String> iterate = deck.iterator();
		while(iterate.hasNext()) {
			System.out.println(iterate.next());
		}
		iterate.remove();
		
	}

}