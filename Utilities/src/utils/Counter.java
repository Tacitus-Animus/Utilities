package utils;

import java.util.Iterator;

public class Counter implements Iterable<Integer> {
	private int count;
	
	public Counter(int count) {
		this.count = count;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < count;
			}

			@Override
			public Integer next() {
				return i++;
			}
			
		};
	}

}
