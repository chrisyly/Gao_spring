package mar29;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.*;

public class Notes {
	public static void main(String[] args) {
		/* Predicate
		 * Boolean-valued functions of one argument. The interface contains various default
		 * methods for composing predicates to complex logical terms(including negate) 
		 */
		Predicate<String> predicate = (s) -> s.length() > 0;
		predicate.test("foo"); //true
		predicate.negate().test("jblong"); // false
		
		Predicate<Boolean> nonNull = Objects::nonNull;
		Predicate<Boolean> isNull = Objects::isNull;
		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> isNotEmpty = isEmpty.negate();
		System.out.println(isEmpty.test("Tmac"));
		System.out.println(isNotEmpty.test("Killer"));
		
		/* Functions
		 * accepts one argument and produce a result, default methods can be used to chain
		 * multiple functions together
		 */
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		System.out.println(backToString.apply("123"));
		
		/* Supplier
		 * produce a result of a given generic type; DONT accept argument
		 */
		Supplier<person> personSupplier = person::new;
		personSupplier.get();
		
		/*Consumers
		 * represents operations to be performed on a single input argument.
		 */
		Consumer<person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
		greeter.accept(new person("Peter", "Jackson"));
		
		/* Comparator
		 * well-known from older versions of Java. 
		 */
		Comparator<person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
		person p1 = new person("Peter", "Jackson");
		person p2 = new person("Allen", "Iverson");
		int ans1 = comparator.compare(p1, p2);
		int ans2 = comparator.reversed().compare(p1, p2);
		System.out.println("Answers are: " + ans1 + " , " + ans2);
		
		/* Optionals
		 * are NOT functional interfaces, instead it's a nifty utility to prevent 
		 * NUllPOINTEREXCEPTION. 
		 */
		Optional<String> optional = Optional.of("bam");
		optional.isPresent(); // true
		optional.get(); // "bam"
		optional.orElse("fallback"); //"bam"
		optional.ifPresent((s) -> System.out.println(s.charAt(0)));
	}
}
