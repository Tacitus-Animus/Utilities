package utils.Collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Data {
	private Data() {}
	public static <T> void swap(T a, T b){
		T temp = a;
		a = b;
		b = temp;
	}
	
		public Set<Integer> findClose(Set<Integer> set, int target){
			var points = new HashSet<Integer>();
			int limit = 2;
			Iterator<Integer> it = set.iterator();
			int point1;
			int lowest1 = Math.abs((point1 = it.next()) - target);
			Integer point2 = null;
			while(it.hasNext() || point2 == null) {
				int check = it.next();
				if((Math.abs(check) - target) < lowest1) {
					  
				}
			}
			
			return null;
		}
}
