import fr.hsh.utils.PropertiesLoader;


public class MemLeakTest {
	public static void main(String[] args) throws ClassNotFoundException {
		while (true) {
			Class clazz = Class.forName(PropertiesLoader.class.getName());
		}
	}
}
