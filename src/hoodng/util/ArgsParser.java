/**
 * Copyright (c) Hu Dong, All rights reserved.
 * 
 * File: ArgsParser.java
 * Create: Sep 4, 2014
 */
package hoodng.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hudong
 *
 */
public final class ArgsParser {

	public static Map<String, Object> parse(String[] args) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String key = null;
		for (int i = 0, len = args.length, count = 0; i < len; i++) {
			String arg = args[i];
			if (arg.startsWith("-")) {
				if (key != null) {
					putArg(map, key, null);
					key = null;
				}
				if (arg.startsWith("--")) {
					int p = arg.indexOf("=");
					if (p != -1) {
						putArg(map, arg.substring(0, p), arg.substring(p + 1));
					} else {
						putArg(map, arg, null);
					}
				} else {
					key = arg;
				}
			} else {
				if (key != null) {
					putArg(map, key, arg);
					key = null;
				} else {
					putArg(map, "$" + count, arg);
					count++;
				}
			}
		}
		if (key != null) {
			putArg(map, key, null);
		}
		return map;
	}

	private static Map<String, Object> putArg(Map<String, Object> map,
			String key, Object value) {
		map.put(argName(key).trim(), value == null ? true : value);
		return map;
	}

	private static String argName(String str) {
		return str.startsWith("--") ? str.substring(2)
				: (str.startsWith("-") ? str.substring(1) : str);
	}
}
