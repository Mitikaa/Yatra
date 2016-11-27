package yatra.dao;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import yatra.models.Station;

/**
 * @author Mitikaa
 *
 */
public class TripDao {

	public void getFilters(Model model){
		runFilterQuery("select DISTINCT ?filters where {?terminal yatra:hasFilter ?filters}",model);
	}
	
	public String getTerminalURIs(Model model, String source, String destination){
		String result = runTerminalQuery("select ?source ?dest WHERE{ "
				+ "{?source yatra:hasTerminalName \""+ source + "\"} UNION"
				+ "{?dest yatra:hasTerminalName \"" + destination + "\"}}",model);
		return result;
	}
	
	public String getTripURI(Model model, String sourceURI, String destURI){
		String result = runTripQuery("select ?trip WHERE{ "
				+ "?trip yatra:hasTerminal1 <"+ sourceURI +">." 
				+ "?trip yatra:hasTerminal2 <"+ destURI + ">.}",model);
		return result;
	}
	
	public String getStationURIs(Model model, String tripURI){
		String result = runStationQuery("select ?stations WHERE{ "
				+ "<" + tripURI +"> yatra:hasStation ?stations }", model);
		return result;
	}
	
	public Station getStationDetails(Model model, String stationURI, String filter){
		Station result = runStationDetailsQuery("select ?name ?latitude ?longitude ?filter ?iconUrl WHERE{ "
				+ "<"+ stationURI +"> yatra:hasStationName ?name."
				+ "<"+ stationURI +"> yatra:hasLatitude ?latitude."
				+ "<"+ stationURI +"> yatra:hasLongitude ?longitude."
				+ "<"+ stationURI +"> yatra:hasFilter ?filter."
				+ "<"+ stationURI +"> yatra:hasIconUrl ?iconUrl."
				+ "FILTER regex(?filter, \""+ filter +"\").}", model);
		return result;
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
	
	private String runTerminalQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
		  StringBuffer result = new StringBuffer();
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
					result.append(source.toString()+"\n");
					}
				else if(dest != null){
					result.append(dest.toString()+"\n");
					}
				else{
					System.err.println("No terminal found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		  return result.toString();
		}
	
	private String runTripQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
		  StringBuffer result = new StringBuffer();
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
					result.append(trip.toString() + "\n");
					}
				else{
					System.err.println("No trip found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		  return result.toString();
		}
	private String runStationQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
		  StringBuffer result = new StringBuffer();
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
					result.append(stations.toString()+"\n");
					}
				else{
					System.err.println("No station found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		  return result.toString();
		}
	
	private Station runStationDetailsQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
		  Station station = new Station();
		  
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
				RDFNode latitude = soln.get("?latitude");
				RDFNode longitude = soln.get("?longitude");
				RDFNode filter = soln.get("?filter");
				RDFNode iconUrl = soln.get("?iconUrl");
				if( name != null){
					station.setName(name.toString());
					station.setFilters(filter.toString());
					station.setLatitude(latitude.toString());
					station.setLongitude(longitude.toString());
					station.setIconUrl(iconUrl.toString());
					}
				else{
					System.err.println("No details found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		  return station;
		}
}
