package yatra.dao;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

/**
 * @author Mitikaa
 *
 */
public class TripDao {

	public void getFilters(Model model){
		runFilterQuery("select DISTINCT ?filters where {?terminal yatra:hasFilter ?filters}",model);
	}
	
	public void getTerminalURIs(Model model){
		System.out.println("Source and Destination URIs");
		String source = "Los Angeles; California";
		String destination = "Chicago; Illinois";
		runTerminalQuery("select ?source ?dest WHERE{ "
				+ "{?source yatra:hasTerminalName \""+ source + "\"} UNION"
				+ "{?dest yatra:hasTerminalName \"" + destination + "\"}}",model);
	}
	
	public void getTripURI(Model model){
		System.out.println("Trips");
		String sourceURI = "http://www.semanticweb.org/ontologies/2016/12/Yatra#terminal2";
		String destURI = "http://www.semanticweb.org/ontologies/2016/12/Yatra#terminal1";
		runTripQuery("select ?trip WHERE{ "
				+ "?trip yatra:hasTerminal1 <"+ sourceURI +">." 
				+ "?trip yatra:hasTerminal2 <"+ destURI + ">.}",model);
	}
	
	public void getStationURIs(Model model){
		System.out.println("StationList");
		String tripURI = "http://www.semanticweb.org/ontologies/2016/12/Yatra#trip54";
		runStationQuery("select ?stations WHERE{ "
				+ "<" + tripURI +"> yatra:hasStation ?stations }", model);
	}
	
	public void getStationDetails(Model model){
		System.out.println("Station");
		String stationURI = "http://www.semanticweb.org/ontologies/2016/12/Yatra#station0";
		String filter = "politics";
		runStationDetailsQuery("select ?name ?latitude ?longitude ?filter ?iconUrl WHERE{ "
				+ "<"+ stationURI +"> yatra:hasStationName ?name."
				+ "<"+ stationURI +"> yatra:hasLatitude ?latitude."
				+ "<"+ stationURI +"> yatra:hasLongitude ?longitude."
				+ "<"+ stationURI +"> yatra:hasFilter ?filter."
				+ "<"+ stationURI +"> yatra:hasIconUrl ?iconUrl."
				+ "FILTER regex(?filter, \""+ filter +"\").}", model);
	}
	
	private void runFilterQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
			
		  // Establish Prefixes
		  //Set default Name space first
		  queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		  queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		  queryStr.append("PREFIX yatra" + ": <" + "http://www.semanticweb.org/ontologies/2016/12/Yatra#" 
		                   + "> ");
				
		  //Now add query
		  queryStr.append(queryRequest);
		  Query query = QueryFactory.create(queryStr.toString());
		  QueryExecution qexec = QueryExecutionFactory.create(query, model);
				
		  try{
			ResultSet response = qexec.execSelect();
				
			while( response.hasNext())
			{
				QuerySolution soln = response.nextSolution();
				RDFNode filter = soln.get("?filters");
				if( filter != null )
					System.out.println(filter.toString() );
				else
					System.err.println("No filters found!");		
			}
		  }
		  finally { 
			  qexec.close();
			  }
		}
	
	private void runTerminalQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
			
		  // Establish Prefixes
		  //Set default Name space first
		  queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		  queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		  queryStr.append("PREFIX yatra" + ": <" + "http://www.semanticweb.org/ontologies/2016/12/Yatra#" 
		                   + "> ");
				
		  //Now add query
		  queryStr.append(queryRequest);
		  Query query = QueryFactory.create(queryStr.toString());
		  QueryExecution qexec = QueryExecutionFactory.create(query, model);
				
		  try{
			ResultSet response = qexec.execSelect();
				
			while( response.hasNext())
			{
				QuerySolution soln = response.nextSolution();
				RDFNode source = soln.get("?source");
				RDFNode dest = soln.get("?dest");
				if( source != null){
					System.out.println(source.toString());
					}
				else if(dest != null){
					System.out.println(dest.toString());
					}
				else{
					System.err.println("No terminal found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		}
	
	private void runTripQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
			
		  // Establish Prefixes
		  //Set default Name space first
		  queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		  queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		  queryStr.append("PREFIX yatra" + ": <" + "http://www.semanticweb.org/ontologies/2016/12/Yatra#" 
		                   + "> ");
				
		  //Now add query
		  queryStr.append(queryRequest);
		  Query query = QueryFactory.create(queryStr.toString());
		  QueryExecution qexec = QueryExecutionFactory.create(query, model);
				
		  try{
			ResultSet response = qexec.execSelect();
				
			while( response.hasNext())
			{
				QuerySolution soln = response.nextSolution();
				RDFNode trip = soln.get("?trip");
				if( trip != null){
					System.out.println(trip.toString());
					}
				else{
					System.err.println("No trip found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		}
	private void runStationQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
			
		  // Establish Prefixes
		  //Set default Name space first
		  queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		  queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		  queryStr.append("PREFIX yatra" + ": <" + "http://www.semanticweb.org/ontologies/2016/12/Yatra#" 
		                   + "> ");
				
		  //Now add query
		  queryStr.append(queryRequest);
		  Query query = QueryFactory.create(queryStr.toString());
		  QueryExecution qexec = QueryExecutionFactory.create(query, model);
				
		  try{
			ResultSet response = qexec.execSelect();
				
			while( response.hasNext())
			{
				QuerySolution soln = response.nextSolution();
				RDFNode stations = soln.get("?stations");
				if( stations != null){
					System.out.println(stations.toString());
					}
				else{
					System.err.println("No station found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		}
	private void runStationDetailsQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
			
		  // Establish Prefixes
		  //Set default Name space first
		  queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		  queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		  queryStr.append("PREFIX yatra" + ": <" + "http://www.semanticweb.org/ontologies/2016/12/Yatra#" 
		                   + "> ");
				
		  //Now add query
		  queryStr.append(queryRequest);
		  Query query = QueryFactory.create(queryStr.toString());
		  QueryExecution qexec = QueryExecutionFactory.create(query, model);
				
		  try{
			ResultSet response = qexec.execSelect();
				
			while( response.hasNext())
			{
				QuerySolution soln = response.nextSolution();
				RDFNode name = soln.get("?name");
				if( name != null){
					System.out.println(name.toString());
					}
				else{
					System.err.println("No details found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		}
}
