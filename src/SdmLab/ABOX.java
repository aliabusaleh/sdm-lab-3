package SdmLab;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
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
            OntModel model;
            Model m = ModelFactory.createDefaultModel().read("data/ontology-tbox.owl");
            model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, m);
            model.setNsPrefix("sdm", "http://www.sdm-lab.com/#");
            // read the JSON file
            JsonReader reader; reader = Json.createReader(Files.newInputStream(Paths.get("data/papers_json.json")));

            OntClass venue = model.getOntClass( model.getNsPrefixURI("sdm") +"Venue");
            OntClass journal = model.getOntClass(  model.getNsPrefixURI("sdm") +"Journal");
            OntClass conference = model.getOntClass(  model.getNsPrefixURI("sdm") +"Conference");
            OntClass symposium = model.getOntClass(  model.getNsPrefixURI("sdm") +"Symposium");
            OntClass expertGroup = model.getOntClass(  model.getNsPrefixURI("sdm") +"ExpertGroup");
            OntClass workshop = model.getOntClass(  model.getNsPrefixURI("sdm") +"Workshop");
            OntClass regular = model.getOntClass(  model.getNsPrefixURI("sdm") +"Regular");
            OntClass person = model.getOntClass(  model.getNsPrefixURI("sdm") +"Person");
            OntClass handlers = model.getOntClass(  model.getNsPrefixURI("sdm") +"Handlers");
            OntClass author = model.getOntClass(  model.getNsPrefixURI("sdm") +"Author");
            OntClass reviewer = model.getOntClass( model.getNsPrefixURI("sdm") +"Reviewer");
            OntClass paper = model.getOntClass( model.getNsPrefixURI("sdm") +"Paper");
            OntClass fullpaper = model.getOntClass( model.getNsPrefixURI("sdm") +"Fullpaper");
            OntClass shortPaper = model.getOntClass( model.getNsPrefixURI("sdm") +"ShortPaper");
            OntClass demoPaper = model.getOntClass( model.getNsPrefixURI("sdm") +"DemoPaper");
            OntClass poster = model.getOntClass( model.getNsPrefixURI("sdm") +"Poster");
            OntClass area = model.getOntClass( model.getNsPrefixURI("sdm") +"Area");
            OntClass publication = model.getOntClass( model.getNsPrefixURI("sdm") +"Publication");
            OntClass volume = model.getOntClass( model.getNsPrefixURI("sdm") +"Volume");
            OntClass proceeding = model.getOntClass( model.getNsPrefixURI("sdm") +"Proceeding");
            OntClass keyword = model.getOntClass( model.getNsPrefixURI("sdm") +"Keyword");
            OntClass review = model.getOntClass( model.getNsPrefixURI("sdm") +"Review");


            ObjectProperty assignsReviewer = model.getObjectProperty( model.getNsPrefixURI("sdm")+"AssignsReviewer");
            ObjectProperty venueHasHandler = model.getObjectProperty(model.getNsPrefixURI("sdm")+"venueHasHandler");
            ObjectProperty writesReview = model.getObjectProperty(model.getNsPrefixURI("sdm")+"WritesReview");
            ObjectProperty reviewsPaper = model.getObjectProperty(model.getNsPrefixURI("sdm")+"ReviewsPaper");
            ObjectProperty writtenBy = model.getObjectProperty(model.getNsPrefixURI("sdm")+"WrittenBy");
            ObjectProperty publishedIn = model.getObjectProperty(model.getNsPrefixURI("sdm")+"PublishedIn");
            ObjectProperty publicationRelatedTo = model.getObjectProperty(model.getNsPrefixURI("sdm")+"PublicationRelatedTo");
            ObjectProperty containsKeyword = model.getObjectProperty(model.getNsPrefixURI("sdm")+"ContainsKeyword");
            ObjectProperty keywordRelatedTo = model.getObjectProperty(model.getNsPrefixURI("sdm")+"KeywordRelatedTo");
            ObjectProperty venueRelatedTo = model.getObjectProperty(model.getNsPrefixURI("sdm")+"VenueRelatedTo");
            ObjectProperty belongsToJournal = model.getObjectProperty(model.getNsPrefixURI("sdm")+"BelongsToJournal");
            ObjectProperty belongsToConference = model.getObjectProperty(model.getNsPrefixURI("sdm")+"BelongsToConference");
            ObjectProperty hasReview = model.getObjectProperty(model.getNsPrefixURI("sdm")+"HasReview");
            ObjectProperty submittedIn = model.getObjectProperty(model.getNsPrefixURI("sdm")+"SubmittedIn");

            DatatypeProperty affiliation = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"affiliation");
            DatatypeProperty doi = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"doi");
            DatatypeProperty edition = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"edition");
            DatatypeProperty h_index = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"h-index");
            DatatypeProperty issn = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"issn");
            DatatypeProperty domain = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"domain");
            DatatypeProperty name = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"name");
            DatatypeProperty bDay = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"Bday");
            DatatypeProperty reviewDecision = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"reviewdecision");
            DatatypeProperty reviewText = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"reviewtext");
            DatatypeProperty role = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"role");
            DatatypeProperty salary = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"role");
            DatatypeProperty title = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"title");
            DatatypeProperty topic = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"topic");
            DatatypeProperty volumeNumber = model.getDatatypeProperty(model.getNsPrefixURI("sdm") +"volumenumber");

            JsonArray papersArray = reader.readArray();
            // loop through the papers array and add each paper to the model
            label:
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

                switch (__paper_type) {
                    case "Fullpaper":
                        // fullpaper
                        __paper = fullpaper;

                        break;
                    case "ShortPaper":
                        // ShortPaper
                        __paper = shortPaper;
                        break;
                    case "DemoPaper":
                        // DemoPaper
                        __paper = demoPaper;
                        break;
                    case "Poster":
                        // it should be published in a conference, otherwise skip it
                        if (__journal_exists.equals("null")) {
                            __paper = poster;
                        } else {
                            System.out.println("Problem!, skipping a paper that has Poster type, but not published in a conference.");
                            break label;
                        }

                        break;
                    default:
                        System.out.println("Problem!, Problem with unknown type!");
                        continue;
                }
                // now create the paper
                Individual __paperInstance = __paper.createIndividual(model.getNsPrefixURI("sdm") +__paper_id);


                String __paper_title = record.getString("title");
                __paperInstance.addProperty(title, __paper_title);

                String __paper_doi = record.getJsonObject("externalIds").getString("DOI", "");
                if (__paper_doi.equals("")) continue; // skip if no doi
                __paperInstance.addProperty(doi, encodeValue(__paper_doi));


                // handlers #todo
                String _handler = record.get("handler").toString();
                Individual __handler = handlers.createIndividual(model.getNsPrefixURI("sdm") +_handler);
                __handler.addProperty(salary, "50,000");
                __handler.addProperty(h_index,  model.createTypedLiteral(Integer.valueOf(90)));

                //loop through the (reviewer) of the paper
                String _reviewText = record.getJsonObject("review").getString("dText");
                String _reviewDecision = record.getJsonObject("review").getString("decision");

                //Review is missing in data
                Individual __review = review.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(__paper_doi +_reviewText));
                __review.addProperty(reviewText, _reviewText);
                __review.addProperty(reviewDecision, _reviewDecision);
                // connect the review with the paper
                __paperInstance.addProperty(hasReview, __review);

                // reviewer info
                JsonArray revArray = record.getJsonArray("reviewers");
                for (int j=0; j < revArray.size()-1; j++){
                    String _reviwer = revArray.getString(j);
                    Individual __reviewer = reviewer.createIndividual(model.getNsPrefixURI("sdm") +_reviwer);
                    __reviewer.addProperty(reviewsPaper, __paperInstance);
                    __reviewer.addProperty(h_index,  model.createTypedLiteral(Integer.valueOf(60)));
                    //Add reviewer writesReview
                    // we assumed that all reviewers writes 1 review only :D
                    __reviewer.addProperty(writesReview,__review);

                    // assign that these reviewer assigned by handler
                    __handler.addProperty(assignsReviewer, __reviewer);

                }

                Individual venueInstance;
                Individual publicationInstance = null;
                // venue info
                // conference
                if (__journal_exists.equals("null")) {
                    // conference
                    String __conference_type = record.getString("conference_type");
                    String __conference_name = record.getString("conference");
                    Individual __conferenceInstance;
                    // try to check of conferance exists
                    __conferenceInstance = model.getIndividual(model.getNsPrefixURI("sdm") +encodeValue(__conference_name));
                    if (__conferenceInstance == null) {
                        switch (__conference_type) {
                            case "Symposium":
                                // Symposium
                                __conferenceInstance = symposium.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(__conference_name));

                                break;
                            case "ExpertGroup":
                                // ExpertGroup
                                __conferenceInstance = expertGroup.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(__conference_name));
                                break;
                            case "Workshop":
                                // Workshop
                                __conferenceInstance = workshop.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(__conference_name));
                                break;
                            case "Regular":
                                //Regular
                                __conferenceInstance = regular.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(__conference_name));
                                break;
                            default:
                                System.out.println("Conference type unkown");
                                continue;
                        }
                        // add the conference handlers :D
                        __conferenceInstance.addProperty(venueHasHandler, __handler);
                        __handler.addProperty(role, "Chair");
                        // we need to add property #todo
//                        __conferenceInstance.addProperty()
                    }
                    venueInstance = __conferenceInstance;
                    //Conference name as attribute
                    __conferenceInstance.addProperty(name, __conference_name);

                    // if a paper is accepted, we have publication-related only
                    // else we ignore this part :D
                    if (_reviewDecision.equals("ACCEPTED")) {
                        //Proceeding is missing - should we add the same name as the conference
                        String _proceeding = record.get("edition").toString();
                        Individual __proceeding = proceeding.createIndividual(model.getNsPrefixURI("sdm") + _proceeding);
                        __proceeding.addProperty(issn, "123450");
                        __proceeding.addProperty(edition, model.createTypedLiteral(Integer.valueOf(1)));

                        // connect paper with publication
                        __paperInstance.addProperty(publishedIn, __proceeding);
                        publicationInstance = __proceeding;

                        //connect proceeding with conference
                        __proceeding.addProperty(belongsToConference, __conferenceInstance);
                    }

                    //connect paper with instance
                    __paperInstance.addProperty(submittedIn, __conferenceInstance);


                }
                // journal
                else {
                    // journal
                    JsonObject _journal = record.getJsonObject("journal");
                    String _journal_name = _journal.getString("name");
                    // create journal instance or get existing one
                    Individual __journal;

                    __journal = model.getIndividual(model.getNsPrefixURI("sdm") +encodeValue(_journal_name));
                    if (__journal == null) __journal = journal.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(_journal_name));
                    // connect paper with it
                    __paperInstance.addProperty(submittedIn, __journal);

                    // if a paper is accepted, we have publication-related only
                    // else we ignore this part :D
                    if (_reviewDecision.equals("ACCEPTED")) {
                        String _journal_volume = String.valueOf(_journal.get("volume"));
                        Individual __volume = volume.createIndividual(model.getNsPrefixURI("sdm") + encodeValue(_journal_volume));
                        String _issn = "0123456";
                        __volume.addProperty(issn, _issn);
                        __volume.addProperty(volumeNumber, model.createTypedLiteral(Integer.valueOf(5)));

                        // connect paper with publication
                        __paperInstance.addProperty(publishedIn, __volume);
                        // connect journal with volume
                        __volume.addProperty(belongsToJournal, __journal);

                        publicationInstance = __volume;
                    }

                    // add handlers assigned by this journal
                    __journal.addProperty(venueHasHandler, __handler);
                    __handler.addProperty(role, "Editor");
                    venueInstance = __journal;

                }

                // loop through the keyword and area and add them to the model

                JsonArray s2FieldsOfStudyArray = record.getJsonArray("s2FieldsOfStudy");
                for (int j = 0; j < s2FieldsOfStudyArray.size(); j++) {
                    JsonObject _keyword = s2FieldsOfStudyArray.getJsonObject(j);

                    String __keywordN = _keyword.getString("category");
                    String __areaN = _keyword.getString("category");

                    // try to find already existing keyword
                    Individual _keyword__ = model.getIndividual(model.getNsPrefixURI("sdm") +encodeValue(__keywordN));
                    if( _keyword__ == null) {
                        _keyword__ = keyword.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(__keywordN));
                        _keyword__.addProperty(domain, "dummy domain");
                    }
                    Individual _area__ = model.getIndividual(model.getNsPrefixURI("sdm") +encodeValue(__areaN));
                    if(_area__ == null) {
                        _area__ = area.createIndividual(model.getNsPrefixURI("sdm") +encodeValue(__areaN));
                        _area__.addProperty(topic, __areaN);
                    }

                    // connect keyword with area
                    _keyword__.addProperty(keywordRelatedTo, _area__);
                    // connect them with paper
                    __paperInstance.addProperty(containsKeyword, _keyword__);

                    //area needs to be connected with journal and conference
                        venueInstance.addProperty(venueRelatedTo, _area__);

                    // if a paper is accepted, we have publication-related only
                    // else we ignore this part :D
                    if (_reviewDecision.equals("ACCEPTED")) {
                        // add publication related to
                        publicationInstance.addProperty(publicationRelatedTo, _area__);
                    }
                }

                // loop through the authors and add them to the model
                JsonArray authorsArray = record.getJsonArray("authors");
                for (int j = 0; j < authorsArray.size(); j++) {
                    JsonObject _author = authorsArray.getJsonObject(j);

                    String __author_id = _author.getString("authorId");
                    String __author_name = _author.getString("name");

                    Individual __author = author.createIndividual( model.getNsPrefixURI("sdm") +__author_id);
                    __author.addProperty(name, __author_name);
                    __author.addProperty(bDay, "6/6/1998");
                    __author.addProperty(h_index, model.createTypedLiteral(Integer.valueOf(12)));

                    __paperInstance.addProperty(writtenBy, __author);

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
            FileWriter  writerStream = new FileWriter("data/ontology-abox.ttl");
            model.writeAll(writerStream, "TTL");
            writerStream.close();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

