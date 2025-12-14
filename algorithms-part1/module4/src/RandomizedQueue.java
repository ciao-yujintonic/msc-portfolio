import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] array;
	private int index; 

	// construct an empty randomized queue
	public RandomizedQueue() {
		array = (Item[]) new Object[1];
		index = 0;
	}
	// is the randomized queue empty?
	public boolean isEmpty() {
		return index == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return index;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		array[index++] = item;
		if (index == array.length) {
			Resize(array.length * 2);
		}
	}

	// remove and return a random item
	public Item dequeue() {
		if(index == 0) throw new java.util.NoSuchElementException();
		int indice = StdRandom.uniform(index);
		Item i = array[indice];
		// swap
		array[indice] = array[--index];
		array[index] = null;
		if (index>0 && index == array.length/4) {
			Resize(array.length/2);
		}
		return i;	
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if(index == 0) throw new java.util.NoSuchElementException();
		return array[StdRandom.uniform(index)];
	}

	// resize the array
	private void Resize(int length) {
		Item[] arr = (Item[]) new Object[length];
		for (int i = 0; i < index; i++) {
			arr[i] = array[i];
		}
		array = arr;
	}

	// return an independent iterator over items in random order
	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			private Item[] arr;
			private int n;
			
			{
				// Initialization block - runs when iterator is created
				arr = (Item[]) new Object[index];
				for (int i = 0; i < index; i++) {
					arr[i] = array[i];
				}
				n = index;
				StdRandom.shuffle(arr); // O(n) linear time complexity
			}

			@Override
			public boolean hasNext() {
				return n > 0;
			}

			@Override
			public Item next() {
				if (n == 0) throw new java.util.NoSuchElementException();
				Item item = arr[--n];
				arr[n] = null;
				return item;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	// main method for testing
	public static void main(String[] args) {
		int n = 5;
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		for (int i = 0; i < n; i++)
		    queue.enqueue(i);
		for (int a : queue) {
		    for (int b : queue)
		        StdOut.print(a + "-" + b + " ");
		    StdOut.println();
		}
		
		
		RandomizedQueue<String> deck = new RandomizedQueue<>();
		deck.enqueue("AA");
		deck.enqueue("BB");
		deck.enqueue("CC");
		deck.enqueue("DD");
		System.out.println(deck.isEmpty());
		System.out.println(deck.dequeue());
		System.out.println(deck.dequeue());
		System.out.println(deck.sample());
	
	
		Iterator<String> iterate = deck.iterator();
		while(iterate.hasNext()) {
			System.out.println(iterate.next());
		}
		iterate.remove();
	}
}