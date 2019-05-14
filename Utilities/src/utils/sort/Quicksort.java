package utils.sort;

import java.util.Comparator;
import java.util.List;

/**
 * This Sort class sorts uses the quicksort algorithm to sort a list of type T
 * <p> using Comparator of type T.
 * @author Alexander J Paul
 * @since 10-OCT-2017
 * @version 1.3
 * @param <T> - The type specific to the this Sort Class.
 */
public class Quicksort<T> implements Sort<T> 
{
	@Override
	public void sort(List<T> list, Comparator<T> compareStrategy) {
		quickSort(list, 0, list.size() - 1, compareStrategy);
	}

	//helper method
	private void quickSort(List<T> list, int front, int end, Comparator<T> compareStrategy) 
	{	
		int pivot_marker = end;
		
		int left_marker = front;
		
		int right_marker =  end - 1;
		
		Outer:
		while(true)
		{
			while(true)
			{
				//see(list, pivot_marker, left_marker, right_marker, monster -> String.format("%5d", monster.getAttack()));
				if(left_marker == pivot_marker) break Outer;
				if(compareStrategy.compare(list.get(left_marker), list.get(pivot_marker)) >= 0) break;
				left_marker++;
			}
			
			while(true)
			{
				//see(monsters, pivot_marker, left_marker, right_marker, monster -> String.format("%5d", monster.getAttack()));
				if(right_marker <= left_marker) break Outer;
				if(compareStrategy.compare(list.get(right_marker), list.get(pivot_marker)) < 0) break;
				right_marker--;
			}
			
			swap(list, left_marker, right_marker);
		
		}
		
		swap(list, left_marker, pivot_marker);
		
		if(left_marker - front > 1) quickSort(list, front , left_marker - 1, compareStrategy); 	
		if(end - left_marker > 1) quickSort(list, left_marker + 1, end, compareStrategy);
	}
	
}
