package com.fernandomantoan.ramaispti.android.support;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class WebClient {
	private DefaultHttpClient client;
	private HttpGet get;
	private HttpResponse response;

	public WebClient(String url) {
		client = new DefaultHttpClient();
		get = new HttpGet(url);
	}

	public String get() {
		String result = null;
		try {
			get.setHeader("Accept", "application/json");
			get.setHeader("Content-type", "application/json");

			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);

			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("StatusCode diferente de 200");
			}
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			String message = "Não foi possível conectar com o servidor";
			Log.e("WEBCLIENT", message, e);
			throw new RuntimeException(message, e);
		}
		return result;
	}
}