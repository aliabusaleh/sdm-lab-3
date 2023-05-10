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

                String __paper_type = record.getString("paper_type");
                String __journal_exists = record.get("journal").toString();


                if (__paper_type.equals("Fullpaper")) {
                    // fullpaper
                    Individual __paper = fullpaper.createIndividual(Base_url + __paper_type);

                } else if (__paper_type.equals("ShortPaper")) {
                    // ShortPaper
                    Individual __paper = shortPaper.createIndividual(Base_url + __paper_type);
                } else if (__paper_type.equals("DemoPaper")) {
                    // DemoPaper
                    Individual __paper = demoPaper.createIndividual(Base_url + __paper_type);
                }
                else if (__paper_type.equals("Poster")) {
                    // it should be published in a conference, otherwise skip it
                    if (__journal_exists == null) {
                        Individual __paper = poster.createIndividual(Base_url + __paper_type);
                        continue;
                    } else {
                        System.out.println("Problem!, skipping a paper that has Poster type, but not published in a conference.");
                        break;
                    }
                }

                String __paper_id = record.getString("paperId");
                String __paper_doi = record.getJsonObject("externalIds").getString("DOI", "");
                if (__paper_doi.equals("")) continue; // skip if no doi
                String __paper_title = record.getString("title");
                // add doi to schema
                Integer __year = record.getInt("year");
                //add year as a property to schema #toadd


                // venue info
                if (__journal_exists == null) {
                    // conference
                    String __conferencename = record.get("conference").toString();
                    Integer __editionN = record.getInt("edition");
                    // System.out.println(__conference_name);
                    String __conference_type = record.get("conference_type").toString();

                    if (__conference_type.equals("Symposium")) {
                        // Symposium
                        Individual __conference = symposium.createIndividual(Base_url + __conference_type);

                    } else if (__conference_type.equals("ExpertGroup")) {
                        // ExpertGroup
                        Individual __conference = expertGroup.createIndividual(Base_url + __conference_type);
                    } else if (__conference_type.equals("Workshop")) {
                        // Workshop
                        Individual __conference = workshop.createIndividual(Base_url + __conference_type);
                    } else if (__conference_type.equals("Regular")) {
                        //Regular
                        Individual __conference = regular.createIndividual(Base_url + __conference_type);
                    }

                    //Conference name as attribute #toAdd
                    //Conference edition as attribute #toAdd
                    //Proceeding is missing - should we add the same name as the conference

                    else {
                        continue;
                    }
                } else {
                    // journal
                    JsonArray journalArray = record.getJsonArray("journal");
                    for (int j = 0; j < journalArray.size(); j++) {
                        JsonObject _journal = journalArray.getJsonObject(j);

                        String __journalName = record.get("name").toString();
                        Integer __volumeN = record.getInt("volume");

                        Individual __journal = journal.createIndividual(Base_url + __journalName);
                        Individual __volume = volume.createIndividual(Base_url + __volumeN);


                        //add property name and Volume #toadd
                        //__journal.addProperty(name,Base_url+ __journalName);
                        //__journal.addProperty(name,Base_url+ __volume);

                        __volume.addProperty(belongsToJournal, __journal);

                        //__journal.addProperty(journalRelatedTo, __area);


                    }
                }

                Individual _paper = paper.createIndividual(Base_url + __paper_id);
//                _paper.addProperty(doi, Base_url + "www.doi.org/"+ __paper_doi);
//                _paper.addProperty(title, Base_url + __paper_title);


                // loop through the keyword and area and add them to the model

                JsonArray s2FieldsOfStudyArray = record.getJsonArray("category");
                for (int j = 0; j < s2FieldsOfStudyArray.size(); j++) {
                    JsonObject _keyword = s2FieldsOfStudyArray.getJsonObject(j);

                    String __keywordN = _keyword.getString("category");
                    String __areaN = _keyword.getString("category");

                    Individual __keyword = keyword.createIndividual(Base_url + __keywordN);
                    Individual __area = area.createIndividual(Base_url + __areaN);

                    _paper.addProperty(containsKeyword, __keyword);
                    __keyword.addProperty(keywordRelatedTo, __area);
                    //area needs to be connected with journal and conference  #toadd

                }

                // loop through the authors and add them to the model
                JsonArray authorsArray = record.getJsonArray("authors");
                for (int j = 0; j < authorsArray.size(); j++) {
                    JsonObject _author = authorsArray.getJsonObject(j);

                    String __author_id = _author.getString("authorId");
                    String __author_name = _author.getString("name");

                    Individual __author = author.createIndividual(Base_url + __author_id);
//                    __author.addProperty(name,Base_url+ __author_name);

                    _paper.addProperty(writtenBy, __author);

                }


                //loop through the reviewer of the paper

                JsonArray revArray = record.getJsonArray("reviewers");
                for (int j = 0; j < revArray.size(); j++) {
                    JsonObject _reviewer = revArray.getJsonObject(j);

                    String __reviewer_id = _reviewer.get("reviewer").toString();

                    Individual __reviewer = reviewer.createIndividual(Base_url + __reviewer_id);
                     __reviewer.addProperty(reviewsPaper, _paper);
                     //Add reviewer writesreview  #toadd
                    //Review is missing in data


                }
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

