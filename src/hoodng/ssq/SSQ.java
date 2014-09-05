/**
 * Copyright (c) Hu Dong, All rights reserved.
 * 
 * File: SSQ.java
 * Create: Sep 4, 2014
 */
package hoodng.ssq;

import java.io.File;
import java.util.List;
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
		Database db = new Database(new File(dbfile));
		List<Record> recs = db.getRecords();
		String start = "99999";
		String end = recs.isEmpty() ? "03000" : recs.get(recs.size() - 1)
				.issue();
		List<Record> nrecs = RFetch.fetch(start, end);
		int count = 0;
		while (!nrecs.isEmpty()) {
			Record rec = nrecs.remove(0);
			if (!rec.issue().equals(end)) {
				recs.add(rec);
				count++;
				System.out.println(rec.toString());
			}
		}
		System.out.println("Fetch " + count + " new records");
		db.save();

		System.out.println("OK!");
	}
}
