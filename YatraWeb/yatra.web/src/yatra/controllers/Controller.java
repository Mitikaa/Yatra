package yatra.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import yatra.model.*;
import yatra.dao.AutomobileDao;
import yatra.dao.PersonDao;
import yatra.dao.TripDao;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/controller/*")
public class Controller extends HttpServlet {
	
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
	
	Controller trip = null;
	Controller automobile = null;
	Controller person = null;
	
	String source = null;
	String destination = null;
	
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        trip = new Controller();
        automobile = new Controller();	
        person = new Controller();
        source = "Los Angeles; California";
        destination = "Chicago; Illinois";
        queryRDFData();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String responseJson = "[{latitude:33.4255000, longitude:-111.9400000},{latitude:38.0053936, longitude:-100.8844774}]";
		String start, end;
		response.setContentType("text/plain");
		response.setContentType("text/plain");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		start = request.getParameter("start");
		end = request.getParameter("end");
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseJson + start + end);
	}
	
	public void queryRDFData(){
		trip.populateTrips(TRIP_RDF);
		automobile.populateAutomobile(AUTOMOBILE_RDF);
		person.populateFriends(FACEBOOK_RDF);
		
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
		
		System.out.println("Person and Friends:");
		Person[] resultPerson = new Person[10];
		resultPerson = personDao.getFriendList(person._friends, "Tempe");
		for(int i=0; i<resultPerson.length; i++){
			if(resultPerson[i]!=null){
				System.out.println(resultPerson[i].toString());
			}
		}
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
}
