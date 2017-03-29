package mar29;

public class Mar29 {
	@FunctionalInterface
	interface Converter<F, T> {
		T convert(F from);
	}
	
	public static void main(String[] args) {
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		Converter<String, Integer> converter_2 = Integer::valueOf;
		Integer converted = converter.convert("123");
		Integer converted_2 = converter_2.convert("123");
		System.out.println(converted);
		System.out.println(converted_2);
	}
	
}
