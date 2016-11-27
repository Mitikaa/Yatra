package yatra.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import yatra.dao.AutomobileDao;
import yatra.dao.PersonDao;
import yatra.dao.TripDao;
import yatra.model.Automobile;
import yatra.model.Person;
import yatra.model.Station;

/**
 * @author Mitikaa
 *
 */
public class TripController {
	public static String TRIP_RDF = "/Users/Mitikaa/git/Yatra/YatraWeb/yatra.web/Datasets/LocationRDF.rdf";
	public static String AUTOMOBILE_RDF = "/Users/Mitikaa/git/Yatra/YatraWeb/yatra.web/Datasets/automobile.rdf";
	public static String FACEBOOK_RDF = "/Users/Mitikaa/git/Yatra/YatraWeb/yatra.web/Datasets/FacebookFriends.rdf";
	public static String defaultNameSpace = "http://www.semanticweb.org/ontologies/2016/12/Yatra#";
	
	Model _trips = null;
	Model _automobiles = null;
	Model _friends = null;
	
	static TripDao tripDao = new TripDao();
	static AutomobileDao automobileDao = new AutomobileDao();
	static PersonDao personDao = new PersonDao();
	
	TripController trip = null;
	TripController automobile = null;
	TripController person = null;
	
	String source = null;
	String destination = null;
	
	public TripController(){
		trip = new TripController();
        //automobile = new TripController();	
        //person = new TripController();
        source = "Los Angeles; California";
        destination = "Chicago; Illinois";
        queryRDFData();
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
	
	private void populateFriends(String filename){
		_friends = ModelFactory.createOntologyModel();
		InputStream filterInstance =  FileManager.get().open(filename);
		_friends.read(filterInstance, defaultNameSpace);
		try {
			filterInstance.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void queryRDFData(){
		trip.populateTrips(TRIP_RDF);
		
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
		
		trip.populateFriends(FACEBOOK_RDF);
		System.out.println("Person and Friends:");
		Person[] resultPerson = new Person[10];
		resultPerson = personDao.getFriendList(trip._friends, "Tempe");
		for(int i=0; i<resultPerson.length; i++){
			if(resultPerson[i]!=null){
				System.out.println(resultPerson[i].toString());
			}
		}
		
		trip.populateAutomobile(AUTOMOBILE_RDF);
		System.out.println("Automobile:");
		String make = "2013 Buick Regal";
		Automobile resultAutomobile = automobileDao.getAutomobile(trip._automobiles, make);
		System.out.println(resultAutomobile);
	}
}
