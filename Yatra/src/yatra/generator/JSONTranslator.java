package yatra.generator;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTranslator {

	private static final String FILE_PATH_AUTOMOBILE = "E:/Study/Fall2016/SER594/Project/Yatra/Yatra/Files/LocationJSONData.txt";
	private static final String FILE_PATH_RDF_AUTOMOBILE = "E:/Study/Fall2016/SER594/Project/Yatra/Yatra/Files/LocationRDF.rdf";

	public static void main(String[] args) {
		JSONTranslator jSONTranslator = new JSONTranslator();
		String rdfString = jSONTranslator.createRdfFromCSVAutomobile();
		jSONTranslator.writeRdfFile(rdfString, FILE_PATH_RDF_AUTOMOBILE);
	}

	public boolean GenerateRDF(JSONObject allTripData) {
		RdfGenerator rdfGenerator = new RdfGenerator();
		String rdfString = rdfGenerator.createRdfFromCSVAutomobile();
		writeRdfFile(rdfString, FILE_PATH_RDF_AUTOMOBILE);
		return false;
	}

	public String createRdfFromCSVAutomobile() {
		StringBuilder rdfString = new StringBuilder();
		rdfString.append("<?xml version=\"1.0\"?>\n");
		rdfString.append("<!DOCTYPE rdf:RDF [\n");
		rdfString.append("	<!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\">\n");
		rdfString.append("	<!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\">\n");
		rdfString.append("	<!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n");
		rdfString.append("	<!ENTITY yatra \"http://www.semanticweb.org/ontologies/2016/12/Yatra#\">\n");
		rdfString.append("]>\n");
		rdfString.append("<rdf:RDF\n");
		rdfString.append("	xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n");
		rdfString.append("	xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n");
		rdfString.append("	xmlns:yatra=\"http://www.semanticweb.org/ontologies/2016/12/Yatra#\">\n");
		rdfString.append(appendCSVDataLocations());
		rdfString.append("</rdf:RDF>");
		// System.out.println(rdfString.toString());
		return rdfString.toString();
	}

	private String appendCSVDataLocations() {
		JSONArray tripData = loadCSVData(FILE_PATH_AUTOMOBILE);
		Map<String, Integer> isPresent = new HashMap<String, Integer>();
		StringBuilder rdfString = new StringBuilder();
		StringBuilder tripBuilder = new StringBuilder();

		if (tripData.length() > 0) {
			try {

				for (int j = 0; j < tripData.length(); j++) {
					JSONObject jsonData = (JSONObject) tripData.get(j);
					JSONObject destinationData = (JSONObject) jsonData.get("Destination");
					JSONObject sourceData = (JSONObject) jsonData.get("Source");
					JSONArray stationsData = (JSONArray) jsonData.get("Stations");
					String destinationKey = destinationData.get("place_id").toString();
					String sourceKey = sourceData.get("place_id").toString();
					int counter = j + 1;
					Integer isKeyPresent = (Integer) isPresent.get(destinationKey);

					tripBuilder.append("	<rdf:Description rdf:about=\"&yatra;trip" + j + "\">\n");

					if (isKeyPresent != null) {
						tripBuilder
								.append("		<yatra:hasTerminal rdf:resource=\"http://www.semanticweb.org/ontologies/2016/12/Yatra/terminal"
										+ isKeyPresent + "\"/>\n");

					} else {
						isPresent.put(destinationKey, counter);

						tripBuilder
								.append("		<yatra:hasTerminal rdf:resource=\"http://www.semanticweb.org/ontologies/2016/12/Yatra/terminal"
										+ counter + "\"/>\n");

						rdfString.append("		<rdf:Description rdf:about=\"&yatra;terminal" + counter + "\">\n");
						rdfString.append("		<yatra:hasName rdf:datatype=\"&xsd;string\">"
								+ destinationData.get("long_name").toString() + "</yatra:hasName>\n");
						rdfString.append("		<yatra:hasLatitude rdf:datatype=\"&xsd;float\">"
								+ destinationData.get("lat").toString() + "</yatra:hasLatitude>\n");
						rdfString.append("		<yatra:hasLongitude rdf:datatype=\"&xsd;float\">"
								+ destinationData.get("lng").toString() + "</yatra:hasLongitude>\n");
						rdfString.append("		<yatra:hasPlaceId rdf:datatype=\"&xsd;string\">"
								+ destinationData.get("place_id").toString() + "</yatra:hasPlaceId>\n");
						rdfString.append("	</rdf:Description>\n");

						counter = counter + 1;
					}

					isKeyPresent = (Integer) isPresent.get(sourceKey);
					if (isKeyPresent != null) {
						tripBuilder
								.append("		<yatra:hasTerminal rdf:resource=\"http://www.semanticweb.org/ontologies/2016/12/Yatra/terminal"
										+ isKeyPresent + "\"/>\n");

					} else {
						isPresent.put(sourceKey, counter);
						tripBuilder
								.append("		<yatra:hasTerminal rdf:resource=\"http://www.semanticweb.org/ontologies/2016/12/Yatra/terminal"
										+ counter + "\"/>\n");

						rdfString.append("		<rdf:Description rdf:about=\"&yatra;terminal" + counter + "\">\n");
						rdfString.append("		<yatra:hasName rdf:datatype=\"&xsd;string\">"
								+ sourceData.get("long_name").toString() + "</yatra:hasName>\n");
						rdfString.append("		<yatra:hasLatitude rdf:datatype=\"&xsd;float\">"
								+ sourceData.get("lat").toString() + "</yatra:hasLatitude>\n");
						rdfString.append("		<yatra:hasLongitude rdf:datatype=\"&xsd;float\">"
								+ sourceData.get("lng").toString() + "</yatra:hasLongitude>\n");
						rdfString.append("		<yatra:hasPlaceId rdf:datatype=\"&xsd;string\">"
								+ sourceData.get("place_id").toString() + "</yatra:hasPlaceId>\n");
						rdfString.append("	</rdf:Description>\n");
					}

					/*
					 * 
					 * "
					 */

					for (int i = 0; i < stationsData.length(); i++) {
						JSONArray intermediateSet = (JSONArray) stationsData.get(i);

						for (int k = 0; k < intermediateSet.length(); k++) {
							JSONObject station = intermediateSet.getJSONObject(k);
							String stationKey = station.get("id").toString();

							Integer isStationPresent = (Integer) isPresent.get(stationKey);

							if (isStationPresent != null) {
								tripBuilder
										.append("		<yatra:hasStation rdf:resource=\"http://www.semanticweb.org/ontologies/2016/12/Yatra/station"
												+ isStationPresent + "\" />\n");
							} else {
								
								tripBuilder
								.append("		<yatra:hasStation rdf:resource=\"http://www.semanticweb.org/ontologies/2016/12/Yatra/station"
										+ ((j * 100) + (i * 10) + k) + "\" />\n");
								
								isPresent.put(sourceKey, ((j * 100) + (i * 10) + k));

								rdfString.append("		<rdf:Description rdf:about=\"&yatra;station"
										+ Integer.toString((j * 100) + (i * 10) + k) + "\">\n");
								if (station.has("localName"))
									rdfString.append("		<yatra:hasName rdf:datatype=\"&xsd;string\">"
											+ station.get("localName").toString() + "</yatra:hasName>\n");

								if (station.has("location")) {
									rdfString.append("		<yatra:hasLatitude rdf:datatype=\"&xsd;float\">"
											+ station.getJSONObject("location").get("lat").toString()
											+ "</yatra:hasLatitude>\n");
									rdfString.append("		<yatra:hasLongitude rdf:datatype=\"&xsd;float\">"
											+ station.getJSONObject("location").get("lng").toString()
											+ "</yatra:hasLongitude>\n");
								}

								if (station.has("placeId"))
									rdfString.append("		<yatra:hasPlaceId rdf:datatype=\"&xsd;string\">"
											+ station.get("placeId").toString() + "</yatra:hasPlaceId>\n");

								if (station.has("id"))
									rdfString.append("		<yatra:hasId rdf:datatype=\"&xsd;string\">"
											+ station.get("id").toString() + "</yatra:hasId>\n");

								if (station.has("typesFilter"))
									rdfString.append("		<yatra:hasFilter rdf:datatype=\"&xsd;string\">"
											+ station.get("typesFilter").toString() + "</yatra:hasFilter>\n");

								if (station.has("iconUrl"))
									rdfString.append("		<yatra:hasIconUrl rdf:datatype=\"&xsd;string\">"
											+ station.get("iconUrl").toString() + "</yatra:hasIconUrl>\n");

								rdfString.append("	</rdf:Description>\n");
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Data appened!");
		
		tripBuilder
		.append("	</rdf:Description>\n");
		
		rdfString.append(tripBuilder.toString());

		return rdfString.toString();
	}

	private JSONArray loadCSVData(String filePath) {
		StringBuilder csvData = null;
		String line;
		try {
			FileInputStream fileStream = new FileInputStream(filePath);
			DataInputStream dataStream = new DataInputStream(fileStream);
			csvData = new StringBuilder();
			while ((line = dataStream.readLine()) != null) {
				csvData.append(line);
			}
			fileStream.close();
			dataStream.close();
			String jsonString = csvData.toString();
			// System.out.print(jsonString);
			return new JSONArray(jsonString);
		} catch (Exception e) {
			System.err.println("Error finding and loading CSV file");
			e.printStackTrace();
		}
		System.out.println("File loaded!");
		return null;
	}

	private void writeRdfFile(String rdfString, String outputFile) {
		PrintWriter out;
		try {
			out = new PrintWriter(outputFile);
			out.println(rdfString);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File written!");
	}

}
