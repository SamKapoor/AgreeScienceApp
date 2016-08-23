package com.agriscienceapp.webservice;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {

	InputStream is = null;
	JSONObject jObj = null;
	String json = "";

	public JSONParser() {
	}

	public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			if (params != null)
				httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			Log.e("checking", url + params);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", ">" + json);
		} catch (Exception e) {
		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj;
	}
	
	public JSONObject getJSONFromUrlForMain(String url, List<NameValuePair> params) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			if (params != null)
				httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			Log.e("checking", url + params);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", ">" + json);
		} catch (Exception e) {
		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj;
	}

	public JSONObject getJSONFromUrl(String url_, JSONObject jsonObject) {
		InputStream inputStream = null;
		String json = "";

		try {
			HttpParams httpParameters = new BasicHttpParams();
		    // Set the timeout in milliseconds until a connection is established.
		    // The default value is zero, that means the timeout is not used. 
		    int timeoutConnection = 20000;
		    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		    // Set the default socket timeout (SO_TIMEOUT) 
		    // in milliseconds which is the timeout for waiting for data.
		    int timeoutSocket = 20000;
		    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httpPost = new HttpPost(url_);		
			
			StringEntity se = new StringEntity(jsonObject.toString(), "UTF-8");
			httpPost.setEntity(se);
			
//			Log.e("request", jsonObject + "");
			
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			
			if(inputStream != null)
				json = convertInputStreamToString(inputStream);
			else
				json = "";
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Log.e("json String ::: ", json+"  >>");
			if(json.trim().length() > 0)
				jObj = new JSONObject(json);			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Log.e("response", ">" + jObj);
		return jObj;
	}
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;
		inputStream.close();
		return result;
	}
	
	public static String makeCall(String url) {

		// string buffers the url
		StringBuffer buffer_string = new StringBuffer(url);
		String replyString = "";

		// instanciate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// instanciate an HttpGet
		HttpGet httpget = new HttpGet(buffer_string.toString());

		try {
			// get the responce of the httpclient execution of the url
			HttpResponse response = httpclient.execute(httpget);
			InputStream is = response.getEntity().getContent();

			// buffer input stream the result
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			// the result as a string is ready for parsing
			replyString = new String(baf.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// trim the whitespaces
		return replyString.trim();
	}
	
	public JSONObject getJSONFromUrl(String url) {
		try {
			Log.i("URL", "========> " + url);
			HttpParams httpParameters = new BasicHttpParams();
		    // Set the timeout in milliseconds until a connection is established.
		    // The default value is zero, that means the timeout is not used. 
		    int timeoutConnection = 4000;
		    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		    // Set the default socket timeout (SO_TIMEOUT) 
		    // in milliseconds which is the timeout for waiting for data.
		    int timeoutSocket = 6000;
		    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpGet httpGet = new HttpGet(url);
			Log.e("checking", url);
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
//			Log.e("JSON", ">" + json);
		} catch (Exception e) {
		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj;
	}
}