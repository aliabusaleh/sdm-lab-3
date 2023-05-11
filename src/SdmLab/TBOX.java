package SdmLab;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TBOX {
    public static void createAndSaveTBOX() {

        try {
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            String Base_url = "http://www.sdm-lab.com/#";

            /**
             * Venue ontology
             * it's about the Venue and related sub clasees
             */

            OntClass venue = model.createClass(Base_url + "Venue");
            // Journal part
            OntClass journal = model.createClass(Base_url + "Journal");

            // conference part
            OntClass conference = model.createClass(Base_url + "Conference");
            OntClass symposium = model.createClass(Base_url + "Symposium");
            OntClass expertGroup = model.createClass(Base_url + "ExpertGroup");
            OntClass workshop = model.createClass(Base_url + "Workshop");
            OntClass regular = model.createClass(Base_url + "Regular");

            // add dependencies between them
            venue.addSubClass(conference);
            venue.addSubClass(journal);

            conference.addSubClass(symposium);
            conference.addSubClass(expertGroup);
            conference.addSubClass(workshop);
            conference.addSubClass(regular);


            /**
             * Peron's related ontology
             */

            // main classes
            OntClass person = model.createClass(Base_url + "Person");
            OntClass handlers = model.createClass(Base_url + "Handlers");
            OntClass author = model.createClass(Base_url + "Author");
            OntClass reviewer = model.createClass(Base_url + "Reviewer");

            // create dependencies
            person.addSubClass(handlers);
            person.addSubClass(author);
            person.addSubClass(reviewer);



            /**
             * Paper related ontology
             */

            // main classes
            OntClass paper = model.createClass(Base_url + "Paper");
            OntClass full_paper = model.createClass(Base_url + "Fullpaper");
            OntClass short_paper = model.createClass(Base_url + "ShortPaper");
            OntClass demo_paper = model.createClass(Base_url + "DemoPaper");
            OntClass poster = model.createClass(Base_url + "Poster");

            // create dependencies
            paper.addSubClass(full_paper);
            paper.addSubClass(short_paper);
            paper.addSubClass(demo_paper);
            paper.addSubClass(poster);

            /**
             * Publication related ontology
             */
            // main classes
            OntClass area = model.createClass(Base_url + "Area");
            OntClass publication = model.createClass(Base_url + "Publication");
            OntClass volume = model.createClass(Base_url + "Volume");
            OntClass proceeding = model.createClass(Base_url + "Proceeding");
            OntClass keyword = model.createClass(Base_url + "Keyword");

            // create dependencies
            publication.addSubClass(volume);
            publication.addSubClass(proceeding);

            /**
             * Review related ontology
             */

            // main classes
            OntClass review = model.createClass(Base_url + "Review");

            /************************************************************************************************************/

            /**
             * Ontology Properties
             */

            ObjectProperty assignsReviewer = model.createObjectProperty( Base_url + "AssignsReviewer");
            assignsReviewer.addDomain(handlers);
            assignsReviewer.addRange(reviewer);
            assignsReviewer.addLabel("Handlers assigns reviewer for a paper", "en");

            ObjectProperty venueHasHandler = model.createObjectProperty( Base_url + "venueHasHandler");
            venueHasHandler.addDomain(venue);
            venueHasHandler.addRange(handlers);
            venueHasHandler.addLabel("Each venue has a handler", "en");

            ObjectProperty writesReview = model.createObjectProperty(Base_url + "WritesReview");
            writesReview.addDomain(reviewer);
            writesReview.addRange(review);
            writesReview.addLabel("Reviewer write a review", "en");

            ObjectProperty reviewsPaper = model.createObjectProperty(Base_url + "ReviewsPaper");
            reviewsPaper.addDomain(reviewer);
            reviewsPaper.addRange(paper);
            reviewsPaper.addLabel("Reviewer reviews a paper", "en");


            ObjectProperty writtenBy = model.createObjectProperty(Base_url + "WrittenBy");
            writtenBy.addDomain(paper);
            writtenBy.addRange(author);
            writtenBy.addLabel("A paper is written by an author", "en");

            ObjectProperty publishedIn = model.createObjectProperty(Base_url + "PublishedIn");
            publishedIn.addDomain(paper);
            publishedIn.addRange(publication);
            publishedIn.addLabel("A paper is published in a publication", "en");

            ObjectProperty publicationRelatedTo = model.createObjectProperty(Base_url + "PublicationRelatedTo");
            publicationRelatedTo.addDomain(publication);
            publicationRelatedTo.addRange(area);
            publicationRelatedTo.addLabel("A publication is related to an area", "en");

            ObjectProperty containsKeyword = model.createObjectProperty(Base_url + "ContainsKeyword");
            containsKeyword.addDomain(paper);
            containsKeyword.addRange(keyword);
            containsKeyword.addLabel("A Paper contains keywords", "en");

            ObjectProperty keywordRelatedTo = model.createObjectProperty(Base_url + "KeywordRelatedTo");
            keywordRelatedTo.addDomain(keyword);
            keywordRelatedTo.addRange(area);
            keywordRelatedTo.addLabel("A Keyword is related to an area", "en");


            ObjectProperty journalRelatedTo = model.createObjectProperty(Base_url + "JournalRelatedTo");
            journalRelatedTo.addDomain(journal);
            journalRelatedTo.addRange(area);
            journalRelatedTo.addLabel("A Journal is related to an area", "en");

            ObjectProperty conferenceRelatedTo = model.createObjectProperty(Base_url + "ConferenceRelatedTo");
            conferenceRelatedTo.addDomain(conference);
            conferenceRelatedTo.addRange(area);
            conferenceRelatedTo.addLabel("A Conference is related to an area", "en");

            ObjectProperty belongsToJournal = model.createObjectProperty(Base_url + "BelongsToJournal");
            belongsToJournal.addDomain(volume);
            belongsToJournal.addRange(journal);
            belongsToJournal.addLabel("A volume belongs to a journal", "en");

            ObjectProperty belongsToConference = model.createObjectProperty(Base_url + "BelongsToConference");
            belongsToConference.addDomain(proceeding);
            belongsToConference.addRange(conference);
            belongsToConference.addLabel("A proceeding belongs to a conference", "en");


            ObjectProperty hasReview = model.createObjectProperty(Base_url + "HasReview");
            hasReview.addDomain(paper);
            hasReview.addRange(review);
            hasReview.addLabel("A paper has a review", "en");



            ObjectProperty submittedIn = model.createObjectProperty(Base_url + "SubmittedIn");
            submittedIn.addDomain(paper);
            submittedIn.addRange(venue);
            submittedIn.addLabel("A paper submitted in a venue", "en");


            /************************************************************************************************************/

            /**
             * DatatypeProperty
             */

            DatatypeProperty affiliation = model.createDatatypeProperty( Base_url + "affiliation");
            affiliation.addDomain(person);
            affiliation.addRange(XSD.xstring);
            affiliation.addLabel("A person is affiliated to some organization/university ..etc", "en");

            DatatypeProperty doi = model.createDatatypeProperty(Base_url + "doi");
            doi.addDomain(paper);
            doi.addRange(XSD.xstring);
            doi.addLabel("A paper has a DOI", "en");


            DatatypeProperty edition = model.createDatatypeProperty(Base_url + "edition");
            edition.addDomain(proceeding);
            edition.addRange(XSD.integer);
            edition.addLabel("A proceeding has an edition ", "en");

            DatatypeProperty h_index = model.createDatatypeProperty(Base_url + "h-index");
            h_index.addDomain(person);
            h_index.addRange(XSD.integer);
            h_index.addLabel("A person has an h-index", "en");

            DatatypeProperty issn = model.createDatatypeProperty(Base_url + "issn");
            issn.addDomain(publication);
            issn.addRange(XSD.xstring);
            issn.addLabel("A publication has an issn ( identifier)", "en");

            DatatypeProperty domain = model.createDatatypeProperty(Base_url + "domain");
            domain.addDomain(keyword);
            domain.addRange(XSD.xstring);
            domain.addLabel("A keyword has a domain", "en");

            DatatypeProperty name = model.createDatatypeProperty(Base_url + "name");
            UnionClass unionClass = model.createUnionClass(null, model.createList(person, venue));
            name.addDomain(unionClass);
            name.addRange(XSD.xstring);
            name.addLabel("A person/venue has a name", "en");

            DatatypeProperty Bday = model.createDatatypeProperty(Base_url + "Bday");
            Bday.addDomain(person);
            Bday.addRange(XSD.xstring);
            Bday.addLabel("A person has a bDay", "en");



            DatatypeProperty reviewdecision = model.createDatatypeProperty(Base_url + "reviewdecision");
            reviewdecision.addDomain(review);
            reviewdecision.addRange(XSD.xstring);
            reviewdecision.addLabel("A review has a decision ACCEPTED/REJECTED", "en");

            DatatypeProperty reviewtext = model.createDatatypeProperty(Base_url + "reviewtext");
            reviewtext.addDomain(review);
            reviewtext.addRange(XSD.xstring);
            reviewtext.addLabel("A review has a text", "en");


            DatatypeProperty role = model.createDatatypeProperty(Base_url + "role");
            role.addDomain(handlers);
            role.addRange(XSD.xstring);
            role.addLabel("A handler has a role", "en");

            DatatypeProperty salary = model.createDatatypeProperty(Base_url + "salary");
            salary.addDomain(handlers);
            salary.addRange(XSD.xstring);
            salary.addLabel("A handler has a salary", "en");

            DatatypeProperty title = model.createDatatypeProperty(Base_url + "title");
            title.addDomain(paper);
            title.addRange(XSD.xstring);
            title.addLabel("A paper has a title", "en");

            DatatypeProperty topic = model.createDatatypeProperty(Base_url + "topic");
            topic.addDomain(area);
            topic.addRange(XSD.xstring);
            topic.addLabel("An area has a topic", "en");

            DatatypeProperty volumenumber = model.createDatatypeProperty(Base_url + "volumenumber");
            volumenumber.addDomain(volume);
            volumenumber.addRange(XSD.integer);
            volumenumber.addLabel("A volume has a number", "en");


            FileOutputStream writerStream = new FileOutputStream("data/Tbox-output.ttl");
            model.write(writerStream, "TTL");
            writerStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
