package jenaDemo2;

import java.io.InputStream;

import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.json.*;
import java.io.FileOutputStream;  
import java.io.OutputStream;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;  


public class Start {

	public static void main(String[] args) throws JSONException {
		// read the JSON file
		JsonReader reader;
		try {
			reader = Json.createReader(new FileInputStream("papers_json.json"));
		
		JsonArray papersArray = reader.readArray();

		// create a model and namespace
		Model model = ModelFactory.createDefaultModel();
		String baseUri = "http://example.org/";

		// loop through the papers array and add each paper to the model
		for (int i = 0; i < papersArray.size(); i++) {
		    JsonObject paper = papersArray.getJsonObject(i);
		    // create the paper node
		    Resource paperNode = model.createResource(baseUri + "paper/" + paper.getString("paperId"))
		            .addProperty(DCTerms.title, paper.getString("title"))
		            .addProperty(model.createProperty(baseUri, "abstract"), "this is nice paper!");

		    // loop through the authors and add them to the model
		    JsonArray authorsArray = paper.getJsonArray("authors");
		    for (int j = 0; j < authorsArray.size(); j++) {
		        JsonObject author = authorsArray.getJsonObject(j);

		        // create the author node
		        Resource authorNode = model.createResource(baseUri + "author/" + author.getString("authorId"))
		                .addProperty(model.createProperty(baseUri, "name"), author.getString("name"));

		        // create the relationship between the paper and the author
		        model.add(paperNode, model.createProperty(baseUri, "writtenBy"), authorNode);
		    }
		    

		}
			// print the resulting model
//			model.write(System.out, "TURTLE");
			OutputStream out = new FileOutputStream("output-model.rdf");
			model.write(out, "RDF/XML-ABBREV");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	    
	}

}
