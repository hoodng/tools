/**
 * 
 */
package hoodng.ssq;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hoo
 *
 */
public final class Database {

	private File dbfile;
	private List<Record> recs;

	public Database(File dbfile) {
		this.dbfile = dbfile;
		recs = load(this.dbfile);
	}

	private List<Record> load(File dbfile) {
		ArrayList<Record> list = new ArrayList<Record>(1024);
		if (dbfile.exists()) {
			LineNumberReader fr = null;
			try {
				fr = new LineNumberReader(new FileReader(dbfile));
				String str = fr.readLine();
				while (str != null) {
					Record rec = Record.parse(str, " ");
					if (rec != null) {
						list.add(rec);
					}
					str = fr.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fr.close();
				} catch (Exception e) {
				}
			}
		}
		return list;
	}

	public List<Record> getRecords() {
		return recs;
	}

	public void save() {
		PrintWriter pr = null;
		List<Record> recs = this.getRecords();
		try {
			pr = new PrintWriter(this.dbfile, "utf-8");
			for (Record rec : recs) {
				pr.println(rec.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pr.close();
		}

	}
}
