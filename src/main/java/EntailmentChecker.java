import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;


public class EntailmentChecker {

	private OWLOntologyManager man;
	private OWLReasoner owlReasoner;

	public EntailmentChecker(String ontologyUrl) {
		this.man = OWLManager.createOWLOntologyManager ( );

		IRI efo = IRI.create ( ontologyUrl);
		OWLDataFactory df = man.getOWLDataFactory ( );

		OWLOntology owlOntology5 = null;

		try {
			owlOntology5 = man.loadOntology ( efo );
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace ( );
		}

		OWLReasonerFactory rf = new Reasoner.ReasonerFactory ( );

		this.owlReasoner = rf.createReasoner ( owlOntology5 );
		this.owlReasoner.precomputeInferences ( InferenceType.CLASS_HIERARCHY );




	}


	public boolean confirmSubclass(String diseaseId, String parent) {

		IRI diseaseTermIri = getIriFromShortId ( diseaseId );

		IRI parentIri = getIriFromShortId ( parent );


		return owlReasoner.isEntailed ( getSubClassOfAxiom ( diseaseTermIri, parentIri ) );

	}

	private OWLSubClassOfAxiom getSubClassOfAxiom(IRI child, IRI parent) {
		return man.getOWLDataFactory ().getOWLSubClassOfAxiom (
				man.getOWLDataFactory ().getOWLClass ( child ),
				man.getOWLDataFactory ().getOWLClass ( parent )
				);
	}

	private IRI getIriFromShortId(String id) {

		if (id.startsWith ( "EFO_" ) ) {
			return IRI.create ( "http://www.ebi.ac.uk/efo/"+id );
		} else if (id.startsWith ( "Orphanet_" )) {
			return IRI.create ( "http://www.orpha.net/ORDO/"+id );
		}
		else {
			return IRI.create ( "http://purl.obolibrary.org/obo/"+id );
		}

	}

}


