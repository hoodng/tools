/**
 * 
 */
package hoodng.ssq;

/**
 * @author hoo
 *
 */
public final class Record {

	public static final int REDMAX = 33;
	public static final int BLUMAX = 16;

	private static final ThreadLocal<StringBuilder> M = new ThreadLocal<StringBuilder>() {
		@Override
		protected StringBuilder initialValue() {
			return new StringBuilder(32);
		}

	};

	private String issue;
	private int[] reds;
	private int blue;

	public Record(String issue, int[] reds, int blue) {
		this.issue = issue;
		this.reds = reds;
		this.blue = blue;
	}

	public String issue() {
		return this.issue;
	}

	public int[] reds() {
		return this.reds;
	}

	public int blue() {
		return this.blue;
	}

	public String toString() {
		StringBuilder buf = M.get();
		buf.setLength(0);
		buf.append(this.issue()).append(" ");
		int[] rs = this.reds();
		for (int i = 0, len = rs.length; i < len; i++) {
			buf.append(String.format("%02d ", rs[i]));
		}
		buf.append(String.format("%02d", this.blue()));
		return buf.toString();
	}

	public static Record parse(String str, String split) {
		str = str.trim();
		if (str.isEmpty())
			return null;

		String[] strs = str.split(split);
		int[] reds = new int[6];
		for (int i = 0, len = reds.length; i < len; i++) {
			reds[i] = Integer.parseInt(strs[i + 1]);
		}
		return new Record(strs[0], reds, Integer.parseInt(strs[7]));
	}
}
