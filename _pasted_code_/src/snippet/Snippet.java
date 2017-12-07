package snippet;

public class Snippet {
	 // create a base url, based on API_HOST and SEARCH_PATH
			String url = "http://" + API_HOST + SEARCH_PATH;
			// Convert geo location to geo hash with a precision of 4 (+- 20km)
			String geoHash = GeoHash.encodeGeohash(lat, lon, 4);
			if (term == null) {
				term = DEFAULT_TERM;
			}
			// Encode term in url since it may contain special characters
			term = urlEncodeHelper(term);
	                             // Make your url query part like: "apikey=12345&geoPoint=abcd&keyword=music&radius=50"
			String query = String.format("apikey=%s&geoPoint=%s&keyword=%s&radius=50", API_KEY, geoHash, term);
			try {
				// Open a HTTP connection between your Java application and TicketMaster based on url
				HttpURLConnection connection = (HttpURLConnection) new URL(url + "?" + query).openConnection();
				// Set requrest method to GET
				connection.setRequestMethod("GET");
	
				// Send request to TicketMaster and get response, response code could be returned directly
				// response body is saved in InputStream of connection.
				int responseCode = connection.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url + "?" + query);
				System.out.println("Response Code : " + responseCode);
	
				// Now read response body to get events data
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
	
				JSONObject responseJson = new JSONObject(response.toString());
			              JSONObject embedded = (JSONObject) responseJson.get("_embedded");
			              JSONArray events = (JSONArray) embedded.get("events");
				return events;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	              }
	}
	
}

