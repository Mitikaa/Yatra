package yatra.dao;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import yatra.model.Person;

public class PersonDao {
	public Person[] getFriendList(Model model, String location){
		Person[] result = runFriendListQuery("select ?name ?location where {"
				+ "?person yatra:hasName ?name."
				+ "?person yatra:hasLocation ?location."
				+ "?person yatra:isFriendOf ?me."
				+ "FILTER regex(?location, \""+ location +"\").}",model);
		return result;
	}

	private Person[] runFriendListQuery(String queryRequest, Model model) {
		StringBuffer queryStr = new StringBuffer();
		  Person person = null;
		  Person[] friends = new Person[10];
		  int count=0;
		  
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
				
			while( response.hasNext()&&count<10)
			{
				person = new Person();
				QuerySolution soln = response.nextSolution();
				RDFNode name = soln.get("?name");
				RDFNode location = soln.get("?location");
				if(name != null){
					person.setName(name.toString());
					person.setLocation(location.toString());
					friends[count] = person;
					count++;
					}
				else{
					System.err.println("No friends found!");	
				}
			}
		  }
		  finally { 
			  qexec.close();
			  }
		  return friends;
	}
}
