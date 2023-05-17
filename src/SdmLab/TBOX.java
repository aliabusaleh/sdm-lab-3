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
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            String Base_url = "http://www.sdm-lab.com/#";
            model.setNsPrefix("sdm", Base_url);

            /**
             * Venue ontology
             * it's about the Venue and related sub clasees
             */

            OntClass venue = model.createClass(model.getNsPrefixURI("sdm") + "Venue");
            // Journal part
            OntClass journal = model.createClass(model.getNsPrefixURI("sdm") + "Journal");

            // conference part
            OntClass conference = model.createClass(model.getNsPrefixURI("sdm") + "Conference");
            OntClass symposium = model.createClass(model.getNsPrefixURI("sdm") + "Symposium");
            OntClass expertGroup = model.createClass(model.getNsPrefixURI("sdm") + "ExpertGroup");
            OntClass workshop = model.createClass(model.getNsPrefixURI("sdm") + "Workshop");
            OntClass regular = model.createClass(model.getNsPrefixURI("sdm") + "Regular");

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
            OntClass person = model.createClass(model.getNsPrefixURI("sdm") + "Person");
            OntClass handlers = model.createClass(model.getNsPrefixURI("sdm") + "Handlers");
            OntClass author = model.createClass(model.getNsPrefixURI("sdm") + "Author");
            OntClass reviewer = model.createClass(model.getNsPrefixURI("sdm") + "Reviewer");

            // create dependencies
            person.addSubClass(handlers);
            person.addSubClass(author);
            person.addSubClass(reviewer);



            /**
             * Paper related ontology
             */

            // main classes
            OntClass paper = model.createClass(model.getNsPrefixURI("sdm") + "Paper");
            OntClass full_paper = model.createClass(model.getNsPrefixURI("sdm") + "Fullpaper");
            OntClass short_paper = model.createClass(model.getNsPrefixURI("sdm") + "ShortPaper");
            OntClass demo_paper = model.createClass(model.getNsPrefixURI("sdm") + "DemoPaper");
            OntClass poster = model.createClass(model.getNsPrefixURI("sdm") + "Poster");

            // create dependencies
            paper.addSubClass(full_paper);
            paper.addSubClass(short_paper);
            paper.addSubClass(demo_paper);
            paper.addSubClass(poster);

            /**
             * Publication related ontology
             */
            // main classes
            OntClass area = model.createClass(model.getNsPrefixURI("sdm") + "Area");
            OntClass publication = model.createClass(model.getNsPrefixURI("sdm") + "Publication");
            OntClass volume = model.createClass(model.getNsPrefixURI("sdm") + "Volume");
            OntClass proceeding = model.createClass(model.getNsPrefixURI("sdm") + "Proceeding");
            OntClass keyword = model.createClass(model.getNsPrefixURI("sdm") + "Keyword");

            // create dependencies
            publication.addSubClass(volume);
            publication.addSubClass(proceeding);

            /**
             * Review related ontology
             */

            // main classes
            OntClass review = model.createClass(model.getNsPrefixURI("sdm") + "Review");

            /************************************************************************************************************/

            /**
             * Ontology Properties
             */

            ObjectProperty assignsReviewer = model.createObjectProperty( model.getNsPrefixURI("sdm") + "AssignsReviewer");
            assignsReviewer.addDomain(handlers);
            assignsReviewer.addRange(reviewer);
            assignsReviewer.addLabel("Handlers assigns reviewer for a paper", "en");

            ObjectProperty venueHasHandler = model.createObjectProperty( model.getNsPrefixURI("sdm") + "venueHasHandler");
            venueHasHandler.addDomain(venue);
            venueHasHandler.addRange(handlers);
            venueHasHandler.addLabel("Each venue has a handler", "en");

            ObjectProperty writesReview = model.createObjectProperty(model.getNsPrefixURI("sdm") + "WritesReview");
            writesReview.addDomain(reviewer);
            writesReview.addRange(review);
            writesReview.addLabel("Reviewer write a review", "en");

            ObjectProperty reviewsPaper = model.createObjectProperty(model.getNsPrefixURI("sdm") + "ReviewsPaper");
            reviewsPaper.addDomain(reviewer);
            reviewsPaper.addRange(paper);
            reviewsPaper.addLabel("Reviewer reviews a paper", "en");


            ObjectProperty writtenBy = model.createObjectProperty(model.getNsPrefixURI("sdm") + "WrittenBy");
            writtenBy.addDomain(paper);
            writtenBy.addRange(author);
            writtenBy.addLabel("A paper is written by an author", "en");

            ObjectProperty publishedIn = model.createObjectProperty(model.getNsPrefixURI("sdm") + "PublishedIn");
            publishedIn.addDomain(paper);
            publishedIn.addRange(publication);
            publishedIn.addLabel("A paper is published in a publication", "en");

            ObjectProperty publicationRelatedTo = model.createObjectProperty(model.getNsPrefixURI("sdm") + "PublicationRelatedTo");
            publicationRelatedTo.addDomain(publication);
            publicationRelatedTo.addRange(area);
            publicationRelatedTo.addLabel("A publication is related to an area", "en");

            ObjectProperty containsKeyword = model.createObjectProperty(model.getNsPrefixURI("sdm") + "ContainsKeyword");
            containsKeyword.addDomain(paper);
            containsKeyword.addRange(keyword);
            containsKeyword.addLabel("A Paper contains keywords", "en");

            ObjectProperty keywordRelatedTo = model.createObjectProperty(model.getNsPrefixURI("sdm") + "KeywordRelatedTo");
            keywordRelatedTo.addDomain(keyword);
            keywordRelatedTo.addRange(area);
            keywordRelatedTo.addLabel("A Keyword is related to an area", "en");


            ObjectProperty venueRelatedTo = model.createObjectProperty(model.getNsPrefixURI("sdm") + "VenueRelatedTo");
            venueRelatedTo.addDomain(venue);
            venueRelatedTo.addRange(area);
            venueRelatedTo.addLabel("A Venue is related to an area", "en");


            ObjectProperty belongsToJournal = model.createObjectProperty(model.getNsPrefixURI("sdm") + "BelongsToJournal");
            belongsToJournal.addDomain(volume);
            belongsToJournal.addRange(journal);
            belongsToJournal.addLabel("A volume belongs to a journal", "en");

            ObjectProperty belongsToConference = model.createObjectProperty(model.getNsPrefixURI("sdm") + "BelongsToConference");
            belongsToConference.addDomain(proceeding);
            belongsToConference.addRange(conference);
            belongsToConference.addLabel("A proceeding belongs to a conference", "en");


            ObjectProperty hasReview = model.createObjectProperty(model.getNsPrefixURI("sdm") + "HasReview");
            hasReview.addDomain(paper);
            hasReview.addRange(review);
            hasReview.addLabel("A paper has a review", "en");



            ObjectProperty submittedIn = model.createObjectProperty(model.getNsPrefixURI("sdm") + "SubmittedIn");
            submittedIn.addDomain(paper);
            submittedIn.addRange(venue);
            submittedIn.addLabel("A paper submitted in a venue", "en");


            /************************************************************************************************************/

            /**
             * DatatypeProperty
             */

            DatatypeProperty affiliation = model.createDatatypeProperty( model.getNsPrefixURI("sdm") + "affiliation");
            affiliation.addDomain(person);
            affiliation.addRange(XSD.xstring);
            affiliation.addLabel("A person is affiliated to some organization/university ..etc", "en");

            DatatypeProperty doi = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "doi");
            doi.addDomain(paper);
            doi.addRange(XSD.xstring);
            doi.addLabel("A paper has a DOI", "en");


            DatatypeProperty edition = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "edition");
            edition.addDomain(proceeding);
            edition.addRange(XSD.integer);
            edition.addLabel("A proceeding has an edition ", "en");

            DatatypeProperty h_index = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "h-index");
            h_index.addDomain(person);
            h_index.addRange(XSD.integer);
            h_index.addLabel("A person has an h-index", "en");

            DatatypeProperty issn = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "issn");
            issn.addDomain(publication);
            issn.addRange(XSD.xstring);
            issn.addLabel("A publication has an issn ( identifier)", "en");

            DatatypeProperty year = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "year");
            year.addDomain(venue);
            year.addRange(XSD.xstring);
            year.addLabel("A venue has an year ", "en");

            DatatypeProperty domain = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "domain");
            domain.addDomain(keyword);
            domain.addRange(XSD.xstring);
            domain.addLabel("A keyword has a domain", "en");

            DatatypeProperty personName = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "personName");
            personName.addDomain(person);
            personName.addRange(XSD.xstring);
            personName.addLabel("A person has a name", "en");

            DatatypeProperty venueName = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "venueName");
            venueName.addDomain(venue);
            venueName.addRange(XSD.xstring);
            venueName.addLabel("A person has a name", "en");

            DatatypeProperty name = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "name");
            name.addSubProperty(personName);
            name.addSubProperty(venueName);

            DatatypeProperty Bday = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "Bday");
            Bday.addDomain(person);
            Bday.addRange(XSD.xstring);
            Bday.addLabel("A person has a bDay", "en");

            DatatypeProperty reviewdecision = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "reviewdecision");
            reviewdecision.addDomain(review);
            reviewdecision.addRange(XSD.xstring);
            reviewdecision.addLabel("A review has a decision ACCEPTED/REJECTED", "en");

            DatatypeProperty reviewtext = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "reviewtext");
            reviewtext.addDomain(review);
            reviewtext.addRange(XSD.xstring);
            reviewtext.addLabel("A review has a text", "en");


            DatatypeProperty role = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "role");
            role.addDomain(handlers);
            role.addRange(XSD.xstring);
            role.addLabel("A handler has a role", "en");

            DatatypeProperty salary = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "salary");
            salary.addDomain(handlers);
            salary.addRange(XSD.xstring);
            salary.addLabel("A handler has a salary", "en");

            DatatypeProperty title = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "title");
            title.addDomain(paper);
            title.addRange(XSD.xstring);
            title.addLabel("A paper has a title", "en");

            DatatypeProperty topic = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "topic");
            topic.addDomain(area);
            topic.addRange(XSD.xstring);
            topic.addLabel("An area has a topic", "en");

            DatatypeProperty volumenumber = model.createDatatypeProperty(model.getNsPrefixURI("sdm") + "volumenumber");
            volumenumber.addDomain(volume);
            volumenumber.addRange(XSD.integer);
            volumenumber.addLabel("A volume has a number", "en");


            FileOutputStream writerStream = new FileOutputStream("data/ontology-tbox.owl");
            model.write(writerStream, "RDF/XML");
            writerStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
