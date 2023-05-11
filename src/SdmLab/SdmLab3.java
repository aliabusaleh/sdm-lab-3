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
			// create TBOX
			TBOX.createAndSaveTBOX();
			// Create ABOX
			ABOX.createAndSaveABOX();
		}
		catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	}



}
