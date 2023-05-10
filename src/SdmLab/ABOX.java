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
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ABOX {
    public static void createAndSaveABOX() {
        try {
            // create models instances
            OntModel model = null;
			Model m = ModelFactory.createDefaultModel().read("data/Ontology-output.owl");
			model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, m);
            String Base_url = "http://www.sdm-lab.com/#";
            // read the JSON file
            JsonReader reader;
            reader = Json.createReader(Files.newInputStream(Paths.get("data/papers_json.json")));


            OntClass venue = model.getOntClass( Base_url +"Venue");
            OntClass journal = model.getOntClass(  Base_url +"Journal");
            OntClass conference = model.getOntClass(  Base_url +"Conference");
            OntClass symposium = model.getOntClass(  Base_url +"Symposium");
            OntClass expertGroup = model.getOntClass(  Base_url +"ExpertGroup");
            OntClass workshop = model.getOntClass(  Base_url +"Workshop");
            OntClass regular = model.getOntClass(  Base_url +"Regular");
            OntClass person = model.getOntClass(  Base_url +"Person");
            OntClass handlers = model.getOntClass(  Base_url +"Handlers");
            OntClass author = model.getOntClass(  Base_url +"Author");
            OntClass reviewer = model.getOntClass( Base_url + "Reviewer");
            OntClass paper = model.getOntClass( Base_url + "Paper");
            OntClass fullpaper = model.getOntClass( Base_url + "Fullpaper");
            OntClass shortPaper = model.getOntClass( Base_url + "ShortPaper");
            OntClass demoPaper = model.getOntClass( Base_url + "DemoPaper");
            OntClass poster = model.getOntClass( Base_url + "Poster");
            OntClass area = model.getOntClass( Base_url + "Area");
            OntClass publication = model.getOntClass( Base_url + "Publication");
            OntClass volume = model.getOntClass( Base_url + "Volume");
            OntClass proceeding = model.getOntClass( Base_url + "Proceeding");
            OntClass keyword = model.getOntClass( Base_url + "Keyword");
            OntClass review = model.getOntClass( Base_url + "Review");


            ObjectProperty assignsReviewer = model.getObjectProperty( Base_url +"AssignsReviewer");
            ObjectProperty venueHasHandler = model.getObjectProperty(Base_url +"Has");
            ObjectProperty writesReview = model.getObjectProperty(Base_url +"WritesReview");
            ObjectProperty reviewsPaper = model.getObjectProperty(Base_url +"ReviewsPaper");
            ObjectProperty writtenBy = model.getObjectProperty(Base_url +"WrittenBy");
            ObjectProperty publishedIn = model.getObjectProperty(Base_url +"PublishedIn");
            ObjectProperty publicationRelatedTo = model.getObjectProperty(Base_url +"PublicationRelatedTo");
            ObjectProperty containsKeyword = model.getObjectProperty(Base_url +"ContainsKeyword");
            ObjectProperty keywordRelatedTo = model.getObjectProperty(Base_url +"KeywordRelatedTo");
            ObjectProperty journalRelatedTo = model.getObjectProperty(Base_url +"JournalRelatedTo");
            ObjectProperty conferenceRelatedTo = model.getObjectProperty(Base_url +"ConferenceRelatedTo");
            ObjectProperty belongsToJournal = model.getObjectProperty(Base_url +"BelongsToJournal");
            ObjectProperty belongsToConference = model.getObjectProperty(Base_url +"BelongsToConference");
            ObjectProperty hasReview = model.getObjectProperty(Base_url +"HasReview");
            ObjectProperty submittedIn = model.getObjectProperty(Base_url +"SubmittedIn");

            DatatypeProperty affiliation = model.getDatatypeProperty(Base_url + "affiliation");
            DatatypeProperty doi = model.getDatatypeProperty(Base_url + "doi");
            DatatypeProperty edition = model.getDatatypeProperty(Base_url + "edition");
            DatatypeProperty h_index = model.getDatatypeProperty(Base_url + "h-index");
            DatatypeProperty issn = model.getDatatypeProperty(Base_url + "issn");
            DatatypeProperty domin = model.getDatatypeProperty(Base_url + "domin");
            DatatypeProperty name = model.getDatatypeProperty(Base_url + "name");
            DatatypeProperty reviewdecision = model.getDatatypeProperty(Base_url + "reviewdecision");
            DatatypeProperty reviewtext = model.getDatatypeProperty(Base_url + "reviewtext");
            DatatypeProperty role = model.getDatatypeProperty(Base_url + "role");
            DatatypeProperty title = model.getDatatypeProperty(Base_url + "title");
            DatatypeProperty topic = model.getDatatypeProperty(Base_url + "topic");
            DatatypeProperty volumenumber = model.getDatatypeProperty(Base_url + "volumenumber");

            JsonArray papersArray = reader.readArray();
            // loop through the papers array and add each paper to the model
            for (int i = 0; i < papersArray.size(); i++) {
                JsonObject record = papersArray.getJsonObject(i);

                String __paper_id = record.getString("paperId");
                String __paper_doi = record.getJsonObject("externalIds").getString("DOI", "");
                if (__paper_doi.equals("")) continue; // skip if no doi
                String __paper_title = record.getString("title");


                Individual _paper = paper.createIndividual(Base_url + __paper_id);
                _paper.addProperty(doi, __paper_doi);
                _paper.addProperty(title, __paper_title);
            }



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
