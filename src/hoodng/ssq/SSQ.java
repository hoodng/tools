/**
 * Copyright (c) Hu Dong, All rights reserved.
 * 
 * File: SSQ.java
 * Create: Sep 4, 2014
 */
package hoodng.ssq;

import hoodng.util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author hudong
 *
 */
public final class SSQ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(
				"http://datachart.500.com/ssq/history/newinc/history.php?start=14200&end=09001");
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream ins = entity.getContent();
				ByteArrayOutputStream ous = new ByteArrayOutputStream();
				try {
					FileUtil.copyFile(ins, ous);
					String str = ous.toString("utf-8");
					//System.err.println(str);
					int p0 = str.indexOf("<tr class=\"t_tr1\">");
					int p1 = -1;
					if(p0 != -1){
						p1 = str.indexOf("</tbody>", p0);
					}
					str = str.substring(p0, p1);
					str = str.replaceAll("<!--<td>2</td>-->|&nbsp;|</td></tr>", "");
					str = str.replaceAll("<tr class=\"t_tr1\"><td>","|");
					str = str.replaceAll("</td><td class=\"t_cfont[2|4]\">|</td><td>", ";");
					String[] strs = str.split("\\|");
					System.err.println(p0);
					System.err.println(p1);
					for(int i=0,len=strs.length; i<len; i++){
						System.err.println(strs[i]);
					}
				} finally {
					ins.close();
					ous.close();
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
