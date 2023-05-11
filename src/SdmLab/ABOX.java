package SdmLab;

import jakarta.json.*;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.vocabulary.*;

import java.io.*;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ABOX {
    static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createAndSaveABOX() {
        try {
            // create models instances
            OntModel model = null;
                Model m = ModelFactory.createDefaultModel().read("data/Tbox-output.ttl");
                model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, m);
                model.setNsPrefix("sdm", "http://www.sdm-lab.com/#");
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
            for (int i = 0; i < 100; i++) {
                JsonObject record = papersArray.getJsonObject(i);

                if( record.getJsonArray("reviewers").size()<2){
                    System.out.println("Less than 2 reviewer, skip it");
                    continue;
                }

                String __paper_type = record.getString("paper_type");
                String __journal_exists = record.get("journal").toString();

                String __paper_id = record.getString("paperId");
                OntClass __paper;

                if (__paper_type.equals("Fullpaper")) {
                    // fullpaper
                     __paper = fullpaper;

                } else if (__paper_type.equals("ShortPaper")) {
                    // ShortPaper
                    __paper = shortPaper;
                } else if (__paper_type.equals("DemoPaper")) {
                    // DemoPaper
                     __paper = demoPaper;
                }
                else if (__paper_type.equals("Poster")) {
                    // it should be published in a conference, otherwise skip it
                    if (__journal_exists.equals("null")) {
                         __paper = poster;
                    } else {
                        System.out.println("Problem!, skipping a paper that has Poster type, but not published in a conference.");
                        break;
                    }

                }
                else {
                    System.out.println("Problem!, Problem with unknown type!");
                    continue;
                }
                // now create the paper
                Individual __paperInstance = __paper.createIndividual(Base_url + __paper_id);


                String __paper_title = record.getString("title");
                __paperInstance.addProperty(title, __paper_title);

//                // add doi to schema
//                Integer __year = record.getInt("year");
//                //add year as a property to schema #toadd


                String __paper_doi = record.getJsonObject("externalIds").getString("DOI", "");
                if (__paper_doi.equals("")) continue; // skip if no doi
                __paperInstance.addProperty(doi, encodeValue(__paper_doi));

                // handlers #todo
                String _handler = record.getString("handler");
                Individual __handler = handlers.createIndividual(Base_url + _handler);

                Individual venueInstance;
                // venue info
                // conference
                if (__journal_exists.equals("null")) {
                    // conference
                    Integer __editionN = record.getInt("edition");
                    // System.out.println(__conference_name);
                    String __conference_type = record.getString("conference_type");
                    String __conference_name = record.getString("conference");
                    Individual __conferenceInstance;
                    // try to check of conferance exists
                    __conferenceInstance = model.getIndividual(Base_url + encodeValue(__conference_name));
                    if (__conferenceInstance == null) {
                        if (__conference_type.equals("Symposium")) {
                            // Symposium
                            ;
                            __conferenceInstance = symposium.createIndividual(Base_url + encodeValue(__conference_name));

                        } else if (__conference_type.equals("ExpertGroup")) {
                            // ExpertGroup
                            __conferenceInstance = expertGroup.createIndividual(Base_url + encodeValue(__conference_name));
                        } else if (__conference_type.equals("Workshop")) {
                            // Workshop
                            __conferenceInstance = workshop.createIndividual(Base_url + encodeValue(__conference_name));
                        } else if (__conference_type.equals("Regular")) {
                            //Regular
                            __conferenceInstance = regular.createIndividual(Base_url + encodeValue(__conference_name));
                        } else {
                            System.out.println("Conference type unkown");
                            continue;
                        }
                        // add the conference handlers :D
                        __conferenceInstance.addProperty(venueHasHandler, __handler);
                        // we need to add property #todo
//                        __conferenceInstance.addProperty()
                    }
                    //Conference name as attribute #toAdd
                    //Conference edition as attribute #toAdd


                    //Proceeding is missing - should we add the same name as the conference
                    String _proceeding = record.get("edition").toString();
                    Individual __proceeding = proceeding.createIndividual(Base_url + _proceeding);

                    // connect paper with publication
                    __paperInstance.addProperty(publishedIn, __proceeding);


                    //connect paper with instance
                    __paperInstance.addProperty(submittedIn, __conferenceInstance);

                    //connect proceeding with conference
                    __proceeding.addProperty(belongsToConference, __conferenceInstance);

                }
                // journal
                else {
                    // journal
                    JsonObject _journal = record.getJsonObject("journal");
                    String _journal_name = _journal.getString("name");
                    // create journal instance or get existing one
                    Individual __journal = model.getIndividual(Base_url + encodeValue(_journal_name));
                    if (__journal == null) __journal = journal.createIndividual(Base_url + encodeValue(_journal_name));
                    // connect paper with it
                    __paperInstance.addProperty(submittedIn, __journal);

                    String _journal_volume = String.valueOf(_journal.get("volume"));
                    Individual __volume = volume.createIndividual(Base_url + encodeValue(_journal_volume));

                    // connect paper with publication
                    __paperInstance.addProperty(publishedIn, __volume);
                    // connect journal with volume
                    __volume.addProperty(belongsToJournal, __journal);

                    // add handlers assigned by this journal
                    __journal.addProperty(venueHasHandler, __handler);

                }


                // loop through the keyword and area and add them to the model

                JsonArray s2FieldsOfStudyArray = record.getJsonArray("s2FieldsOfStudy");
                for (int j = 0; j < s2FieldsOfStudyArray.size(); j++) {
                    JsonObject _keyword = s2FieldsOfStudyArray.getJsonObject(j);

                    String __keywordN = _keyword.getString("category");
                    String __areaN = _keyword.getString("category");

                    // try to find already existing keyword
                    Individual _keyword__ = model.getIndividual(Base_url + encodeValue(__keywordN));
                    if( _keyword__ == null) {
                        _keyword__ = keyword.createIndividual(Base_url + encodeValue(__keywordN));
                    }
                    Individual _area__ = model.getIndividual(Base_url + encodeValue(__areaN));
                    if(_area__ == null) {
                        _area__ = area.createIndividual(Base_url + encodeValue(__areaN));
                    }

                    // connect keyword with area
                    _keyword__.addProperty(keywordRelatedTo, _area__);
                    // connect them with paper
                    __paperInstance.addProperty(containsKeyword, _keyword__);

                    //area needs to be connected with journal and conference  #toadd

                }

                // loop through the authors and add them to the model
                JsonArray authorsArray = record.getJsonArray("authors");
                for (int j = 0; j < authorsArray.size(); j++) {
                    JsonObject _author = authorsArray.getJsonObject(j);

                    String __author_id = _author.getString("authorId");
                    String __author_name = _author.getString("name");

                    Individual __author = author.createIndividual( __author_id);
                    __author.addProperty(name,Base_url+ __author_name);

                    __paperInstance.addProperty(writtenBy, __author);

                }
                //loop through the reviewer of the paper
                String _reviewText = record.getJsonObject("review").getString("dText");
                String _reviewDecision = record.getJsonObject("review").getString("decision");

                //Review is missing in data
                Individual __review = review.createIndividual(Base_url + __paper_doi +_reviewText);
                __review.addProperty(reviewtext, _reviewText);
                __review.addProperty(reviewdecision, _reviewDecision);
                // connect the review with the paper
                __paperInstance.addProperty(hasReview, __review);

                // reviewer info
                JsonArray revArray = record.getJsonArray("reviewers");
                for (int j=0; j < revArray.size()-1; j++){
                    String _reviwer = revArray.getString(j);
                    Individual __reviewer = reviewer.createIndividual(Base_url + _reviwer);
                    __reviewer.addProperty(reviewsPaper, __paperInstance);
                    //Add reviewer writesReview
                    // we assumed that all reviewers writes 1 review only :D
                    __reviewer.addProperty(writesReview,__review);

                    // assign that these reviewer assigned by handler
                    __handler.addProperty(assignsReviewer, __reviewer);
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
            FileWriter  writerStream = new FileWriter("data/Abox-output.ttl");
            model.write(writerStream, "ttl");
            writerStream.close();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

