package com.ecs.latitude.badge;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Key;

public class PublicBadgeSample {

	private static final String RESPONSE_TYPE = "json";
	private static final String APP_NAME = "Google-Latitue-Public-Badge-Sample";
	private static final String PUBLIC_BADGE_URL = "http://www.google.com/latitude/apps/badge/api";

	// Create our transport.
	private static final HttpTransport transport = new ApacheHttpTransport();
	
	public static void main(String[] args) throws Exception {

		HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
		HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(PUBLIC_BADGE_URL));
		request.url.put("user", "INSERT USER ID HERE");
		request.url.put("type", RESPONSE_TYPE);
		//System.out.println(request.execute().parseAsString());
		PublicBadge publicBadge = request.execute().parseAs(PublicBadge.class);
		System.out.println("Longitude " + publicBadge.features[0].geometry.coordinates[0]);
		System.out.println("Latitude " + publicBadge.features[0].geometry.coordinates[1]);
	}
	
	public static HttpRequestFactory createRequestFactory(final HttpTransport transport) {
		   
		  return transport.createRequestFactory(new HttpRequestInitializer() {
		   public void initialize(HttpRequest request) {
		    GoogleHeaders headers = new GoogleHeaders();
		    headers.setApplicationName(APP_NAME);
		    request.headers=headers;
		    JsonHttpParser parser = new JsonHttpParser();
		    parser.jsonFactory = new JacksonFactory();
		    request.addParser(parser);
		   }
		});
	}
	
	public static class PublicBadge {

		@Key
		public Feature[] features;
		
		@Key
		public String type;
		
		public static class Feature {
			@Key
			public Geometry geometry;
		}

		public static class Geometry {
			@Key
			public float[] coordinates;
		}

	}
}
