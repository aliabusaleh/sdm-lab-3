package SdmLab;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.json.JSONException;
import jakarta.json.JsonArray;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SdmLab3 {

	public static void main(String[] args) throws JSONException {
		try {

//			//===============================================
//			// Getting all OntClasses (concepts) from SdmLab.TBOX
//			//===============================================
//
//			OntModel model = null;
//			OntClass person = model.getOntClass(constants.BASE_URI.concat("Person"));
//			OntClass author = model.getOntClass(constants.BASE_URI.concat("Author"));
//			OntClass chair = model.getOntClass(constants.BASE_URI.concat("Chair"));
//			OntClass editor = model.getOntClass(constants.BASE_URI.concat("Editor"));
//			OntClass reviewer = model.getOntClass(constants.BASE_URI.concat("Reviewer"));
//			OntClass paper = model.getOntClass(constants.BASE_URI.concat("Paper"));
//			OntClass fullPaper = model.getOntClass(constants.BASE_URI.concat("Full_Paper"));
//			OntClass shortPaper = model.getOntClass(constants.BASE_URI.concat("Short_Paper"));
//			OntClass demoPaper = model.getOntClass(constants.BASE_URI.concat("Demo_Paper"));
//			OntClass posterPaper = model.getOntClass(constants.BASE_URI.concat("Poster_Paper"));
//			OntClass year = model.getOntClass(constants.BASE_URI.concat("Year"));
//			OntClass venue = model.getOntClass(constants.BASE_URI.concat("Venue"));
//			OntClass conference = model.getOntClass(constants.BASE_URI.concat("Conference"));
//			OntClass journal = model.getOntClass(constants.BASE_URI.concat("Journal"));
//			OntClass workshop = model.getOntClass(constants.BASE_URI.concat("Workshop"));
//			OntClass symposium = model.getOntClass(constants.BASE_URI.concat("Symposium"));
//			OntClass expertGroup = model.getOntClass(constants.BASE_URI.concat("Expert_Group"));
//			OntClass regularConference = model.getOntClass(constants.BASE_URI.concat("Regular_Conference"));
//			OntClass decision = model.getOntClass(constants.BASE_URI.concat("Decision"));
//			OntClass acceptOrRejected = model.getOntClass(constants.BASE_URI.concat("Accepted_Or_Rejected"));
//			OntClass reviewText = model.getOntClass(constants.BASE_URI.concat("Review_Text"));
//			OntClass areas = model.getOntClass(constants.BASE_URI.concat("Areas"));
//			OntClass publications = model.getOntClass(constants.BASE_URI.concat("Publications"));
//
//
//			// create models instances
//			Model m = ModelFactory.createDefaultModel().read("data/ontology-v2.ttl");
//			model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, m);
//
//			// read the JSON file
//			JsonReader reader;
//
//
//			reader = Json.createReader(Files.newInputStream(Paths.get("data/papers_json.json")));
//			JsonArray papersArray = reader.readArray();
//
//			// loop through the papers array and add each paper to the model
//			for (int i = 0; i < papersArray.size(); i++) {
//				JsonObject paper = papersArray.getJsonObject(i);
//
//
//			}
//
//			model.write(System.out, "N-TRIPLE");
			TBOX.createAndSaveTBOX();
			ABOX.createAndSaveABOX();
		}
		catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	}



}
