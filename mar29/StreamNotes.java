package mar29;

import java.util.*;

public class StreamNotes {
	public static void main(String[] args) {
		List<String> stringCollection = new ArrayList<String>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		
		// filter()
		stringCollection
			.stream()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println); // 返回所有开头是a的string
		
		// Sorted()
		stringCollection
		    .stream()
		    .sorted()
		    /* .filter((s) -> s.startsWith("a"))
		     * 如果加上上一行就是加一个filter, 所有以a开头的  
		     */
		    .forEach(System.out::println); // 排序所有string并且输出
		
		/* Map
		 * intermediate operation MAP converts each element into 
		 * another object via the given function, also use MAP to
		 * transform each object into another type.
		 */
		stringCollection
			.stream()
			.map(String::toUpperCase)
			.sorted((a,b) -> b.compareTo(a))
			.forEach(System.out::println); // 把所有的string变成大写之后, 排序, 打印
		
		/* Match
		 * Used to check whether a certain predicate matches the stream.
		 * ALL the operations are TERMINAL and return a boolean result
		 */
		boolean anyStartsWithA =
				stringCollection
					.stream()
					.anyMatch((s) -> s.startsWith("a"));
		System.out.println(anyStartsWithA);	//true	
		
		boolean allStartsWithA = 
				stringCollection
					.stream()
					.allMatch((s) -> s.startsWith("a"));
		System.out.println(allStartsWithA);	//false
		
		boolean noneStartsWithA = 
				stringCollection
					.stream()
					.noneMatch((s) -> s.startsWith("a"));
		System.out.println(noneStartsWithA);	//false
		
		/* Count
		 * TERMINAL operation returning the number of elements in the stream a LONG
		 */
		long startsWithB = 
				stringCollection
				.stream()
				.filter((s) -> s.startsWith("b"))
				.count();
		System.out.println("The number of words starts with b is: " + startsWithB);
		
	}
}
