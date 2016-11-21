package yatra.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import yatra.dao.AutomobileDao;
import yatra.dao.TripDao;
import yatra.model.Automobile;
import yatra.model.Station;

/**
 * @author Mitikaa
 *
 */
public class CommonController {
	public static String TRIP_RDF = "/Users/Mitikaa/git/Yatra/Yatra/Files/LocationRDF.rdf";
	public static String AUTOMOBILE_RDF = "/Users/Mitikaa/git/Yatra/Yatra/Files/automobile.rdf";
	static String defaultNameSpace = "http://www.semanticweb.org/ontologies/2016/12/Yatra#";
	Model _trips = null;
	Model _automobiles = null;
	static TripDao tripDao = new TripDao();
	static AutomobileDao automobileDao = new AutomobileDao();
	
	public static void main(String[] args){
		CommonController trip = new CommonController();
		CommonController automobile = new CommonController();
		
		trip.populateTrips(TRIP_RDF);
		automobile.populateAutomobile(AUTOMOBILE_RDF);
		
		//for sparql
		String source = "Los Angeles; California";
		String destination = "Chicago; Illinois";
		
		//filters 
		//System.out.println("Filters:");
		//tripDao.getFilters(trip._trips);
		
		System.out.println("Terminals:");
		String resultTerminal = tripDao.getTerminalURIs(trip._trips, source, destination);
		
		//store source and destination URIs
		String[] resultURIs = resultTerminal.split("\n");
		String sourceURI = resultURIs[0];
		String destinationURI = resultURIs[1];
		System.out.println(sourceURI+"\n"+destinationURI);
		
		System.out.println("Trips:");
		String resultTrip = tripDao.getTripURI(trip._trips, sourceURI, destinationURI);
		
		//store trip URIs
		String[] tripURIs = resultTrip.split("\n");
		String tripURI = tripURIs[0];
		System.out.println(tripURI);
		
		System.out.println("Stations:");
		String resultStationList = tripDao.getStationURIs(trip._trips, tripURI);
		
		//store station URis
		String[] stationURIs = resultStationList.split("\n");
		String filter = "point_of_interest";
		for (int i=0; i<stationURIs.length; i++){			
			Station resultStationDetails = tripDao.getStationDetails(trip._trips, stationURIs[i], filter);
			if(resultStationDetails!=null){
				System.out.println(resultStationDetails.toString());
			}
		}
		
		//checking for automobile
		System.out.println("Automobiel:");
		String make = "2013 Buick Regal";
		Automobile resultAutomobile = automobileDao.getAutomobile(automobile._automobiles, make);
		System.out.println(resultAutomobile);
	}
	
	private void populateTrips(String filename){
		_trips = ModelFactory.createOntologyModel();
		InputStream filterInstance =  FileManager.get().open(filename);
		_trips.read(filterInstance, defaultNameSpace);
		try {
			filterInstance.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void populateAutomobile(String filename){
		_automobiles = ModelFactory.createOntologyModel();
		InputStream filterInstance =  FileManager.get().open(filename);
		_automobiles.read(filterInstance, defaultNameSpace);
		try {
			filterInstance.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}