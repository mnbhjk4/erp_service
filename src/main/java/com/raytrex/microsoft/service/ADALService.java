package com.raytrex.microsoft.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class ADALService {
	static Logger log = Logger.getLogger(ADALService.class);
	public static final String client_id = "7c3abeb3-bb33-435a-9ad6-d63ff39e8179";
	public static final String tenantid = "raytrex.onmicrosoft.com";
	public static final String client_secert = "JmAXcEV54v1kbG2jTuA4JwW";

	public String getToken(String authCode, String scope, String redirectUri) {
		try {
			URI uri = new URI("https://login.microsoftonline.com/raytrex.onmicrosoft.com/oauth2/v2.0/token");
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost();
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setURI(uri);

			ArrayList<NameValuePair> formNameValuePairList = new ArrayList<NameValuePair>();
			formNameValuePairList.add(new BasicNameValuePair("grant_type", "authorization_code"));
			formNameValuePairList.add(new BasicNameValuePair("client_id", client_id));
			if (redirectUri.indexOf("http") != -1) {
				formNameValuePairList.add(new BasicNameValuePair("client_secret", client_secert));
			}
			formNameValuePairList.add(new BasicNameValuePair("code", authCode));
			formNameValuePairList.add(new BasicNameValuePair("scope", scope));
			formNameValuePairList.add(new BasicNameValuePair("redirect_uri", redirectUri));
			post.setEntity(new UrlEncodedFormEntity(formNameValuePairList));

			HttpResponse response = client.execute(post);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'POST' request to URL : " + uri.toString());
			log.info("Post parameters : " + formNameValuePairList);
			log.info("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "[]";
	}

	public String getTokenByRefreshToken(String refresh_token, String scope, String redirectUri) {
		try {
			URI uri = new URI("https://login.microsoftonline.com/raytrex.onmicrosoft.com/oauth2/v2.0/token");
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost();
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setURI(uri);

			ArrayList<NameValuePair> formNameValuePairList = new ArrayList<NameValuePair>();
			formNameValuePairList.add(new BasicNameValuePair("grant_type", "refresh_token"));
			formNameValuePairList.add(new BasicNameValuePair("client_id", client_id));
			if (redirectUri.indexOf("http") != -1) {
				formNameValuePairList.add(new BasicNameValuePair("client_secret", client_secert));
			}
			formNameValuePairList.add(new BasicNameValuePair("refresh_token", refresh_token));
			formNameValuePairList.add(new BasicNameValuePair("scope", scope));
			formNameValuePairList.add(new BasicNameValuePair("redirect_uri", redirectUri));
			post.setEntity(new UrlEncodedFormEntity(formNameValuePairList));

			HttpResponse response = client.execute(post);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'POST' request to URL : " + uri.toString());
			log.info("Post parameters : " + formNameValuePairList);
			log.info("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "[]";
	}

	public String getUserMailList(String access_token) {
		URI uri;
		try {
			uri = new URI("https://graph.microsoft.com/v1.0/me/messages");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet();
			get.addHeader("Authorization", "Bearer " + access_token);
			get.addHeader("Host", "graph.microsoft.com");
			get.setURI(uri);

			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'GET' request to URL : " + uri.toString());
			log.info("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			Gson gson = new Gson();
			Map json = gson.fromJson(result.toString(), Map.class);
			return json.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public byte[] getUserProfilePhoto(String access_token, String uid, String name) {
		byte[] image = new byte[0];
		try {
			URI uri = new URI("https://graph.microsoft.com/v1.0/users/" + uid + "/photo/$value");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet();
			get.addHeader("Authorization", "Bearer " + access_token);
			get.setURI(uri);

			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'GET' request to URL : " + uri.toString());
			log.info("Response Code : " + responseCode);

			if (responseCode >= 200 && responseCode <= 299) {
				InputStream input = response.getEntity().getContent();

				if (input != null) {
					image = IOUtils.toByteArray(input);
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	public String listUsers(String access_token) {
		URI uri;
		try {
			uri = new URI("https://graph.microsoft.com/v1.0/users");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet();
			get.addHeader("Authorization", "Bearer " + access_token);
			get.addHeader("Content-Type", "application/json");
			get.setURI(uri);

			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'GET' request to URL : " + uri.toString());
			log.info("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String listMyContacts(String access_token) {
		URI uri;
		try {
			uri = new URI("https://graph.microsoft.com/v1.0/me/contacts");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet();
			get.addHeader("Authorization", "Bearer " + access_token);
			get.addHeader("Content-Type", "application/json");
			get.setURI(uri);

			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'GET' request to URL : " + uri.toString());
			log.info("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String getUserProfile(String access_token, String uid) {
		try {
			URI uri = new URI("https://graph.microsoft.com/v1.0/users/" + uid);
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet();
			get.addHeader("Authorization", "Bearer " + access_token);
			get.setURI(uri);

			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'GET' request to URL : " + uri.toString());
			log.info("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
