/**
 * 
 */
package utils.Collections;


/**
 * @author Alex Paul
 *
 */
public class LinkedList<T> 
{		
		private LinkedList<T> prev, next;
		
		private T element;
			
		public LinkedList(T element) {
			
			this.element = element;
		}

		public LinkedList<T> getPrev() {
			return prev;
		}

		private void setPrev(LinkedList<T> prev) 
		{
			this.prev = prev;
		}

		public LinkedList<T> getNext() {
			return next;
		}

		public void setNext(LinkedList<T> next) 
		{
			this.next = next;
			
			next.setPrev(this);
		}

		public T getElement() {
			return element;
		}

		public boolean hasPrev(){
			return prev != null;
		}
		
		public boolean hasNext() {
			return next != null;
		}
		
		public boolean isEmpty() {
			return element == null;
		}
		
		public int getIndex() {
			return hasPrev() ? 1 + prev.getIndex() : 0;
		}
		
		public int getSize() {
			return 1 + getIndex() + forwardCount();
		}

		private int forwardCount() 
		{
			return hasNext() ? 1 + next.forwardCount() : 0;
		}
		
		public LinkedList<T> getIndex(int index) 
		{
			int result = index - getIndex();
			if(result == 0) return this;
			if(result < 0)  return prev.getIndex(index);
			if(result > 0)  return next.getIndex(index);
							return null;
		}

		public void remove(int index)
		{
			getIndex(index).remove();
		}
		//Can't remove head and tail nodes.
		private void remove() 
		{
			if(!hasPrev()) 
			{
				element = next.element;
				next = next.next;
				next.prev = this;
			}else if(!hasNext())
			{
				element = prev.element;
				prev = prev.prev;
				prev.next = this;
			}else {
				next.setPrev(prev);
				prev.setNext(next);
				next = prev = null;
				element = null;
			}
		}
		
		public String peek() 
		{
			String index = String.valueOf(getIndex() + 1) + ". ";
			String name = isEmpty() ? "Empty" : element.toString();
			return "Link: " + index + name;
		}
		
		
		private String backwardPeek()
		{
			return !hasPrev() ? "" : prev.backwardPeek() + 
									   prev.peek() + "\n";
		}
		
		private String forwardPeek() 
		{
			return !hasNext() ? "" : next.peek() + "\n" + 
									    next.forwardPeek();
		}
		
		/**
		 * @return List form of monster names from all the linked Rooms.
		 */
		public String peekAll() 
		{
			return backwardPeek() +
				   peek() + "\n" + 
				   forwardPeek();
		}

		public void add(int index, LinkedList<T> newRoom) 
		{
			LinkedList<T> prevRoom = getIndex(index);
			
			LinkedList<T> nextRoom = prevRoom.next;
			
			prevRoom.setNext(newRoom);
			
			newRoom.setNext(nextRoom);
		}

}
