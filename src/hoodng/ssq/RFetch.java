/**
 * 
 */
package hoodng.ssq;

import hoodng.util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author hoo
 *
 */
public final class RFetch {

	public static List<Record> fetch(Database db) throws Exception {
		List<Record> recs = db.getRecords();
		String start = "99999";
		String end = recs.isEmpty() ? "03000" : recs.get(recs.size() - 1)
				.issue();

		LinkedList<Record> list = new LinkedList<Record>();

		StringBuilder url = new StringBuilder(
				"http://datachart.500.com/ssq/history/newinc/history.php?");
		url.append("start=").append(start).append("&end=").append(end);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url.toString());
		CloseableHttpResponse response;

		response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream ins = entity.getContent();
			ByteArrayOutputStream ous = new ByteArrayOutputStream();
			try {
				FileUtil.copyFile(ins, ous);
				String str = ous.toString("utf-8");
				int p0 = str.indexOf("<tr class=\"t_tr1\">");
				int p1 = -1;
				if (p0 != -1) {
					p1 = str.indexOf("</tbody>", p0);
				}
				str = str.substring(p0, p1);
				str = str.replaceAll("<!--<td>2</td>-->|&nbsp;|</td></tr>", "");
				str = str.replaceAll("<tr class=\"t_tr1\"><td>", "|");
				str = str.replaceAll(
						"</td><td class=\"t_cfont[2|4]\">|</td><td>", ";");
				String[] strs = str.split("\\|");

				for (int i = 0, len = strs.length; i < len; i++) {
					Record rec = parse(strs[i]);
					if (rec != null) {
						list.addFirst(rec);
					}
				}
			} finally {
				ins.close();
				ous.close();
			}
		}

		int count = 0;
		while (!list.isEmpty()) {
			Record rec = list.remove(0);
			if (!rec.issue().equals(end)) {
				recs.add(rec);
				count++;
				System.out.println(rec.toString());
			}
		}
		System.out.println("Fetch " + count + " new records");
		db.save();

		return recs;
	}

	private static Record parse(String str) {
		str = str.trim();
		return str.isEmpty() ? null : Record.parse(str, ";");
	}
}
