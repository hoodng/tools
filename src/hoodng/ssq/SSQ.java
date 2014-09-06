/**
 * Copyright (c) Hu Dong, All rights reserved.
 * 
 * File: SSQ.java
 * Create: Sep 4, 2014
 */
package hoodng.ssq;

import java.util.List;
import java.io.File;
import java.util.Map;

import hoodng.util.ArgsParser;

/**
 * @author hudong
 *
 */
public final class SSQ {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Map<String, Object> Args = ArgsParser.parse(args);
		String dbfile = Args.containsKey("db") ? (String) Args.get("db")
				: "ssqdb.txt";
		boolean unfecth = Args.containsKey("without-fetch");

		Database db = new Database(new File(dbfile));
		List<Record> recs = !unfecth ? RFetch.fetch(db) : db.getRecords();
		BitMap bitmap = new BitMap(recs);

		if (Args.containsKey("show-bitmap")) {
			showBitmap(bitmap);
		}

		System.out.println("OK!");
	}
	
	private static void showBitmap(BitMap bitmap){
		for (int i = 0, len = bitmap.size(); i < len; i++) {
			System.out.println(bitmap.toString(i));
		}
	}

}
