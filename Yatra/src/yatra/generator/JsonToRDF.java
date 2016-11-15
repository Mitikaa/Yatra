/**
 * 
 */
package yatra.generator;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 * @author Shuchir
 *
 */

public class JsonToRDF {

	private static final String FILE_PATH_AUTOMOBILE = "E:/Study/Fall2016/SER594/Project/Yatra/Yatra/Files/LocationJSONData.txt";
	private static final String FILE_PATH_RDF_AUTOMOBILE = "E:/Study/Fall2016/SER594/Project/Yatra/Yatra/Files/Location.rdf";

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
		JSONObject tripData = loadCSVData(FILE_PATH_AUTOMOBILE);
		StringBuilder rdfString = new StringBuilder();
		System.out.println(tripData);
		/*
		for (int i = 1; i < csvData.size() - 1; i++) {
			ArrayList<String> row = csvData.get(i);
			rdfString.append("		<rdf:Description rdf:about=\"&yatra;automobile" + i + "\">\n");
			rdfString.append(
					"		<yatra:hasVehicle rdf:datatype=\"&xsd;string\">" + row.get(0) + "</yatra:hasVehicle>\n");
			rdfString.append(
					"		<yatra:hasMilage rdf:datatype=\"&xsd;float\">" + row.get(2) + "</yatra:hasMilage>\n");
			rdfString.append(
					"		<yatra:hasImage rdf:datatype=\"&xsd;string\">" + row.get(1) + "</yatra:hasImage>\n");
			rdfString.append(
					"		<yatra:hasEconomy rdf:datatype=\"&xsd;float\">" + row.get(3) + "</yatra:hasEconomy>\n");
			rdfString.append("	</rdf:Description>\n");
		}*/
		System.out.println("Data appened!");
		return rdfString.toString();
	}

	@SuppressWarnings("deprecation")
	private JSONObject loadCSVData(String filePath) {
		StringBuilder csvData = null;
		ArrayList<String> row = null;
		try {
			FileInputStream fileStream = new FileInputStream(filePath);
			DataInputStream dataStream = new DataInputStream(fileStream);
			csvData = new StringBuilder();
			while ((dataStream.readLine()) != null) {
				csvData.append(row);
			}
			fileStream.close();
			dataStream.close();
			return new JSONObject(csvData.toString());
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
