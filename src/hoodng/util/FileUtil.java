/**
 * Copyright (c) Hu Dong, All rights reserved.
 * 
 * File: FileUtil.java
 * Create: Sep 4, 2014
 */
package hoodng.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author hudong
 *
 */
public final class FileUtil {
	/**
	 * Copy file from <code>InputStream</code> to <code>OutputStream</code>
	 * 
	 * @param ins
	 * @param ous
	 * @return
	 * @throws IOException
	 */
	public static long copyFile(InputStream ins, OutputStream ous)
			throws IOException {
		long ret = 0;
		byte[] buf = new byte[2048];
		int rb = -1;
		while ((rb = ins.read(buf)) != -1) {
			ous.write(buf, 0, rb);
			ret += rb;
		}
		ous.flush();
		return ret;
	}
}
