package com.raytrex.erp.service.login.microsoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class ADALService {
	public static final String client_id = "77544d4f-2184-4b4c-8760-f3a4474897f9";
	public static final String tenantid = "raytrex.onmicrosoft.com";
	public static final String client_secert = "gDgF9FdPKLk2uf9A2mOMh1V";
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
			Gson gson = new Gson();
			Map json = gson.fromJson(result.toString(), Map.class);
			if(json.containsKey("access_token")){
				return getUserMailList(json.get("access_token").toString());
			}
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
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
	
}
