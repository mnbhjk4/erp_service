package com.raytrex.erp.service.login.microsoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;


@Service
public class ADALService {
	public static final String client_id = "7c3abeb3-bb33-435a-9ad6-d63ff39e8179";
	public static final String tenantid = "raytrex.onmicrosoft.com";
	public static final String client_secert = "JmAXcEV54v1kbG2jTuA4JwW";
	public String getToken(String authCode,String scope,String redirectUri){
		try{
			URI uri = new URI("https://login.microsoftonline.com/raytrex.onmicrosoft.com/oauth2/v2.0/token");
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new  HttpPost();
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setURI(uri);
			
			ArrayList<NameValuePair> formNameValuePairList = new ArrayList<NameValuePair>();
			formNameValuePairList.add(new BasicNameValuePair("grant_type","authorization_code"));
			formNameValuePairList.add(new BasicNameValuePair("client_id", client_id));
			if(redirectUri.indexOf("localhost") != -1){
				formNameValuePairList.add(new BasicNameValuePair("client_secret",client_secert));
			}
			formNameValuePairList.add(new BasicNameValuePair("code",authCode));
			formNameValuePairList.add(new BasicNameValuePair("scope",scope));
			formNameValuePairList.add(new BasicNameValuePair("redirect_uri",redirectUri));
			post.setEntity(new UrlEncodedFormEntity(formNameValuePairList));
			
			HttpResponse response = client.execute(post);
			int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("Sending 'POST' request to URL : " + uri.toString());
			System.out.println("Post parameters : " + formNameValuePairList);
			System.out.println("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(
		                new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "[]";
	}
	
	public String getTokenByRefreshToken(String refresh_token,String scope,String redirectUri){
	try{
		URI uri = new URI("https://login.microsoftonline.com/raytrex.onmicrosoft.com/oauth2/v2.0/token");
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new  HttpPost();
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setURI(uri);
		
		ArrayList<NameValuePair> formNameValuePairList = new ArrayList<NameValuePair>();
		formNameValuePairList.add(new BasicNameValuePair("grant_type","refresh_token"));
		formNameValuePairList.add(new BasicNameValuePair("client_id", client_id));
		if(redirectUri.indexOf("localhost") != -1){
			formNameValuePairList.add(new BasicNameValuePair("client_secret",client_secert));
		}
		formNameValuePairList.add(new BasicNameValuePair("refresh_token",refresh_token));
		formNameValuePairList.add(new BasicNameValuePair("scope",scope));
		formNameValuePairList.add(new BasicNameValuePair("redirect_uri",redirectUri));
		post.setEntity(new UrlEncodedFormEntity(formNameValuePairList));
		
		HttpResponse response = client.execute(post);
		int responseCode = response.getStatusLine().getStatusCode();

		System.out.println("Sending 'POST' request to URL : " + uri.toString());
		System.out.println("Post parameters : " + formNameValuePairList);
		System.out.println("Response Code : " + responseCode);

		BufferedReader rd = new BufferedReader(
	                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return "[]";
	}
	
	public String getUserMailList(String access_token){
		URI uri;
		try {
			uri = new URI("https://graph.microsoft.com/v1.0/me/messages");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new  HttpGet();
			get.addHeader("Authorization", "Bearer "+access_token);
			get.addHeader("Host", "graph.microsoft.com");
			get.setURI(uri);
			
			
			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("Sending 'GET' request to URL : " + uri.toString());
			System.out.println("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(
		                new InputStreamReader(response.getEntity().getContent()));

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
	
	public String getUserProfilePhoto(String access_token){
		URI uri;
		try {
			uri = new URI("https://graph.microsoft.com/v1.0/me/photo");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new  HttpGet();
			get.addHeader("Authorization", "Bearer "+access_token);
			get.setURI(uri);
			
			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("Sending 'GET' request to URL : " + uri.toString());
			System.out.println("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(
		                new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			Gson gson = new Gson();
			Map json = gson.fromJson(result.toString(), Map.class);
			if(!json.containsKey("@odata.id")){
				return "[]";
			}
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
}
