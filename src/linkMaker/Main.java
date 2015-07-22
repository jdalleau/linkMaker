package linkMaker;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import fr.kevindalleau.Mapper.Mapper;


public class Main {
		
	public static void main(String[] args) {
		
		Mapper mapper = new Mapper();
		
		LinkedList<DrugGenePair> pairs = DrugGenePair.getAssociatedPairs();
		HashMap<String,ArrayList<GeneDiseasePair>> geneDiseasePairs = GeneDiseasePair.getGeneDiseasesPairs();
		HashMap<String, ArrayList<DrugDiseasePair>> drugDiseasePairs = DrugDiseasePair.getDrugDiseasesPairs();
		HashMap<String,String> geneEntrezLinks = Gene.getPharmgkbIDEntrezIDLinks();
		HashMap<String,ArrayList<String>> geneAttributes = Gene.getGeneAttributes();
		Iterator<DrugGenePair> iterator = pairs.iterator();
		while(iterator.hasNext()) {
		 DrugGenePair pair = iterator.next();
		 Gene gene = pair.getGene();
		 Drug drug = pair.getDrug();
		 gene.setEntrez_id(geneEntrezLinks.get(gene.getPharmgkb_id()));
		 gene.setAttributes(geneAttributes.get(gene.getEntrez_id()));
		 drug.setStitch_ids(mapper.getStitch_from_PharmGKB(drug.getPharmgkb_id()));

		}
		
		System.out.println(drugDiseasePairs.toString());
		 
	}
	
	public static String stripURI(String in) {
		return in.replaceAll("(.*)/", "");
	}; 
		
}
