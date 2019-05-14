package utils.sort;

import java.util.Comparator;
import java.util.List;

/**
 * This abstract class is the template used for sorting a list of type T
 * <p> using Comparator of type T..
 * @author Alexander J Paul
 * @since 10-OCT-2017
 * @version 1.3
 * @param <T> - The type specific to the this Sort Class.
 */
public interface Sort<T> {

	/**
	 * @param list - List to sort.
	 * @param compareStrategy - The Comparator used to sort the list.
	 */
	public void sort(List<T> list, Comparator<T> compareStrategy);
	
	/**
	 * @param list - List to swap 2 elements in.
	 * @param indexA - The first element index to swap.
	 * @param indexB - The second element index to swap.
	 */
	default void swap(List<T> list, int indexA, int indexB) 
	{		
		T tempElement = list.get(indexA);
		list.set(indexA, list.get(indexB));
		list.set(indexB, tempElement);
	}
	
}
