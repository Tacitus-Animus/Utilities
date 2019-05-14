package utils.Collections;

import java.util.Comparator;
import java.util.Objects;

public class Tree<T> {

	Node<T> root = null;	
	
	private Comparator<T> comparator;
	
	public Tree(Comparator<T> comparator) {
		this.setComparator(comparator);
	}
	
	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
	public void add(T object) {
		Objects.requireNonNull(object);
		if(root == null) root = new Node<>(object);
		else root.add(object);
	}

	private class Node<R extends T> {
		
		private R object;
		
		private Node<R> big = null;
		private Node<R> small = null;
		private Node<R> same = null;
		
		private Node(R object){
			this.object = object;
		}

		//naive... so naive, I don't know how to add the weird looking thing above 'a' in "naive"... if you're reading this, I might be lying.
		//on a serious note, trait pattern and some serious research in binary tree implementation is required.
		public void add(R object) {
			int result = comparator.compare(object, this.object);
			if(result == 1) {
				if(big == null) big = new Node<>(object);
				else big.add(object);
			}else if(result == 0) {
				if(same == null) same = new Node<>(object);
				else same.add(object);
			}else if(small == null) small = new Node<>(object);
				else small.add(object);
			
		}
		
	}
	
}
