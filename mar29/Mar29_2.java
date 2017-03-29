package mar29;


public class Mar29_2 {
	@FunctionalInterface
	interface Converter<F, T> {
		T convert(F from);
	}
	public static void main(String[] args) {
		something something = new something();
		Converter<String, String> converter = something::startsWith;
		String converted = converter.convert("java");
		System.out.println(converted);
	}
}
