package utils.search;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * This Functional Interface is the template used for searching a list of type T
 * <p>finding a match of a search value mapped to specific attribute of T specified by searchStrategy.
 * @author Alexander J Paul
 * @since 10-OCT-2017
 * @version 1.3
 * @param <T> - The type specific to the this Functional Interface.
 * @param <R>
 */
@FunctionalInterface
public interface Search<T,R> {

	/**
	 * 
	 * @param list - The list to be searched
	 * @param searchStrategy - The BiFunction used to compare R to T which will output an Integer.
	 * @param searchValue - The value used in searching for matching type T.
	 * @return Optional of type T - If searchValue matches a specific attribute specified by searchStrategy returns T or returns empty.
	 */
	public Optional<T> search(ArrayList<T> list, BiFunction<R, T, Integer> searchStrategy, R searchValue);
	
}
