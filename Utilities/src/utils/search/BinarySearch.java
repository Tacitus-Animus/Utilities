package utils.search;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;

import fp.TailCallRecursion.TailCall;
import fp.TailCallRecursion.TailCalls;

/**
 * @author Alex Paul
 *
 * @param <T>
 */
public class BinarySearch<T,R> implements Search<T,R> {
	
	@Override
	public Optional<T> search(ArrayList<T> list, BiFunction<R, T, Integer> searchStrategy, R searchValue) 
	{
		return Optional.ofNullable(binarySearch(list, 0, list.size() - 1, searchStrategy, searchValue).invoke());
	}

	private TailCall<T> binarySearch(ArrayList<T> list, int front, int end, BiFunction<R, T, Integer> searchStrategy, R searchValue)
	{ 
		int mid = front + ((end - front) / 2);
				
		int result = searchStrategy.apply(searchValue, list.get(mid));
						
		if(result == 0) return TailCalls.done(list.get(mid));
		
		if(result < 0 && mid > 0) return TailCalls.call(() -> binarySearch(list, front, mid - 1, searchStrategy, searchValue));
		
		if(result > 0 && end > mid) return TailCalls.call(() -> binarySearch(list, mid + 1, end, searchStrategy, searchValue));
		
		
		return TailCalls.done(null);
		
	}
	
	
	
}
