package yatra.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.json.*;

public class ApiClient {

	static String myApiKey = "YourKey";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] myPlaces = { "Los Angeles; California", "Chicago; Illinois" };
		// ,"Houston; Texas", "Philadelphia; Pennsylvania", "Phoenix; Arizona",
		// "San Antonio; Texas", "New York City; New York",
		// "San Diego; California", "Dallas; Texas", "San Jose; California",
		// "Austin; Texas" };

		String myPlacesCoordinatesAPI = "http://maps.google.com/maps/api/geocode/json?address=%1$s";
		JSONObject[] myCoordinates = new JSONObject[myPlaces.length];

		try {
			for (int i = 0; i < myPlaces.length; i++) {
				StringBuilder jsonResults = new StringBuilder();
				URL url = new URL(String.format(myPlacesCoordinatesAPI, URLEncoder.encode(myPlaces[i], "UTF-8")));
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String str;

				TimeUnit.MILLISECONDS.sleep(500);

				while ((str = in.readLine()) != null) {
					jsonResults.append(str);
				}

				JSONObject jsonObj = new JSONObject(jsonResults.toString());
				JSONArray results = (JSONArray) jsonObj.get("results");

				JSONObject location = (JSONObject) ((JSONObject) ((JSONObject) results.get(0)).get("geometry"))
						.get("location");
				myCoordinates[i] = location.append("long_name", myPlaces[i]);

			}
			System.out.println(getNearbyData(myCoordinates));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONArray getNearbyData(JSONObject[] myCoordinates) {
		String myIntermediateLocationAPI = "https://maps.googleapis.com/maps/api/elevation/json?path=%1$s&samples=%2$d&key=%3$s";
		int sampleSize = 10;
		JSONArray nearByData = new JSONArray();
		for (int i = 0; i < myCoordinates.length; i++) {
			String path = "%1$s,%2$s|%3$s,%4$s";
			JSONObject source = myCoordinates[i];
			for (int j = i + 1; j < myCoordinates.length; j++) {
				StringBuilder jsonResults = new StringBuilder();
				JSONObject destination = myCoordinates[j];
				try {
					URL url = new URL(String.format(myIntermediateLocationAPI, String.format(path, source.get("lat"),
							source.get("lng"), destination.get("lat"), destination.get("lng")), sampleSize, myApiKey));

					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					TimeUnit.MILLISECONDS.sleep(500);
					String str;
					while ((str = in.readLine()) != null) {
						jsonResults.append(str);
					}

					JSONObject jsonObj = new JSONObject(jsonResults.toString());
					JSONArray results = (JSONArray) jsonObj.get("results");

					for (int k = 0; k < results.length(); k++) {
						JSONObject location = (JSONObject) ((JSONObject) results.get(k)).get("location");
						JSONArray myNearbyPlaces = getNearByLocations(location);
						JSONObject tripData = new JSONObject();
						tripData.put("Source", source);
						tripData.put("Destination", destination);
						tripData.put("Stations", myNearbyPlaces);
						
						nearByData.put(tripData);
					}
					return nearByData;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static JSONArray getNearByLocations(JSONObject location) {

		String myNearByPlacesAPI = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%1$s,%2$s&radius=10000&type=&key=%3$s";
		StringBuilder jsonResults = new StringBuilder();
		try {
			URL url = new URL(String.format(myNearByPlacesAPI, location.get("lat"), location.get("lng"), myApiKey));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String str;

			TimeUnit.MILLISECONDS.sleep(500);

			while ((str = in.readLine()) != null) {
				jsonResults.append(str);
			}
			JSONArray localAttractionArray = new JSONArray();
			JSONObject allResults = new JSONObject(jsonResults.toString());
			JSONArray results = (JSONArray) allResults.get("results");
			for (int i = 0; i < results.length(); i++) {
				JSONObject result = (JSONObject) results.get(i);
				JSONObject localAttraction = new JSONObject();
				
				if (result.has("geometry")) {
					if (((JSONObject) result.get("geometry")).has("location")) {
						localAttraction.put("location", ((JSONObject) result.get("geometry")).get("location"));
					}
				}
				if (result.has("name")) 
					localAttraction.put("localName", result.get("name"));
				
				if (result.has("id")) 
					localAttraction.put("id", result.get("id"));

				if (result.has("icon"))
					localAttraction.put("iconUrl", result.get("icon"));

				if (result.has("opening_hours"))
					localAttraction.put("openingHours", result.get("opening_hours"));

				if (result.has("place_id"))
					localAttraction.put("placeId", result.get("place_id"));

				if (result.has("types"))
					localAttraction.put("typesFilter", result.get("types").toString().replace(',', '|'));

				localAttractionArray.put(i, localAttraction);
			}
			
			return localAttractionArray;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
