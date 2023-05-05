package SdmLab;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.vocabulary.*;

import java.io.FileOutputStream;

public class ABOX {
    public static void createAndSaveABOX() {
        try {
            // create models instances
            OntModel model = null;
			Model m = ModelFactory.createDefaultModel().read("data/Ontology-output.owl");
			model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, m);
            String Base_url = "http://www.sdm-lab.com/#";


            OntClass paper = model.getOntClass( Base_url + "Conference");
            OntClass reviewer = model.getOntClass( Base_url + "Reviewer");
            ObjectProperty reviewsPaper = model.getObjectProperty( Base_url +"ReviewsPaper");
            OntClass venue = model.getOntClass( Base_url +"Venue");
            OntClass journal = model.getOntClass(  Base_url +"Journal");
            OntClass conference = model.getOntClass(  Base_url +"Conference");
            ObjectProperty submittedIn = model.createObjectProperty(Base_url +"submittedIn");






            Individual paper1 = paper.createIndividual(Base_url + "This is a nice paper");

            Individual reviewer1 = reviewer.createIndividual(Base_url + "Ali Abu Saleh");
            Individual reviewer2 = reviewer.createIndividual(Base_url + "Ali Abu Saleh 2");
            Individual journal1 = journal.createIndividual(Base_url + "Journaaaaaaaaaaaal");
            Individual conference1 = conference.createIndividual(Base_url + "Conferenceeeeeeeeee");


            paper1.addProperty(reviewsPaper, reviewer1);
            paper1.addProperty(reviewsPaper, reviewer2);
            paper1.addProperty(submittedIn, journal1);



            // Create a reasoner and bind it to the ontology
            Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
            reasoner.bindSchema(model);

            // Validate the ontology and check for inconsistencies
            InfModel inf = ModelFactory.createInfModel(reasoner, model);
            if (inf.validate().isValid()) {
                System.out.println("Ontology is valid");
            } else {
                System.out.println("Ontology is inconsistent");
            }
            FileOutputStream writerStream = new FileOutputStream("data/Abox-output.owl");
            model.write(writerStream, "RDF/XML-ABBREV");
            writerStream.close();



        }
        catch (Exception e){
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }
}
