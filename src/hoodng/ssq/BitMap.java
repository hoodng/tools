/**
 * 
 */
package hoodng.ssq;

import java.util.List;

/**
 * @author hoo
 *
 */
public final class BitMap {

	private static final int COLMAX = Record.REDMAX + Record.BLUMAX;

	private static StringBuilder buf = new StringBuilder(49);

	private byte[][] bits;

	public BitMap(List<Record> recs) {
		bits = new byte[recs.size()][COLMAX];
		for (int i = 0, len = recs.size(); i < len; i++) {
			Record rec = recs.get(i);
			int[] reds = rec.reds();
			for (int ri = 0, rlen = reds.length; ri < rlen; ri++) {
				bits[i][reds[ri] - 1] = 1;
			}
			bits[i][Record.REDMAX + rec.blue() - 1] = 1;
		}
	}

	public int size() {
		return bits.length;
	}

	public String toString(int index) {
		buf.setLength(0);
		byte[] data = bits[index];
		for (int i = 0, len = data.length; i < len; i++) {
			if (data[i] == 0) {
				buf.append(" ");
			} else {
				buf.append("*");
			}
		}
		return buf.toString();
	}
}
