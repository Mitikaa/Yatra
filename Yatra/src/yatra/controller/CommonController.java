package yatra.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import yatra.dao.TripDao;

/**
 * @author Mitikaa
 *
 */
public class CommonController {
	public static String TRIP_RDF = "/Users/Mitikaa/Desktop/Fall 2016/Semantic Web/Yatra/Yatra/Files/LocationRDF.rdf";
	static String defaultNameSpace = "http://www.semanticweb.org/ontologies/2016/12/Yatra#";
	Model _trips = null;
	static TripDao tripDao = new TripDao();
	
	public static void main(String[] args){
		CommonController trip = new CommonController();
		trip.populateTrips();
		System.out.println("Filters:");
		tripDao.getFilters(trip._trips);
	}
	
	private void populateTrips(){
		_trips = ModelFactory.createOntologyModel();
		InputStream filterInstance =  FileManager.get().open(TRIP_RDF);
		_trips.read(filterInstance, defaultNameSpace);
		try {
			filterInstance.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
