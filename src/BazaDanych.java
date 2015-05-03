import java.util.TreeMap;

public class BazaDanych extends TreeMap<String, Ngram> {

	private static final long serialVersionUID = 1L;

	@Override
	public Ngram put(String key, Ngram value) {
		Ngram tmp = this.get(key);
		if (tmp == null) {
			return super.put(key, value);
		} else {
			tmp.addSufiks(value.getFirstSufiks());
			return tmp;
		}
		
	}

}
