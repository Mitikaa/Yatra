package yatra.dao;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import yatra.model.Automobile;
import yatra.model.Station;

/**
 * @author Mitikaa
 *
 */
public class AutomobileDao {
	public Automobile getAutomobile(Model model, String make){
		Automobile result = runMileageQuery("select ?make ?mileage ?image ?economy where {"
				+ "?automobile yatra:hasVehicle ?make."
				+ "?automobile yatra:hasMilage ?mileage."
				+ "?automobile yatra:hasImage ?image."
				+ "?automobile yatra:hasEconomy ?economy."
				+ "FILTER regex(?make, \""+ make +"\").}",model);
		return result;
	}
	
	private Automobile runMileageQuery(String queryRequest, Model model){
		
		  StringBuffer queryStr = new StringBuffer();
		  Automobile automobile = new Automobile();
		  
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
				RDFNode make = soln.get("?make");
				RDFNode mileage = soln.get("?mileage");
				RDFNode image = soln.get("?image");
				RDFNode economy = soln.get("?economy");
				if(make != null){
					automobile.setVehicle(make.toString());
					automobile.setMileage(mileage.toString());
					automobile.setImage(image.toString());
					automobile.setEconomy(economy.toString());
					}
				else{
					System.err.println("No details found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		  return automobile;
		}
}
