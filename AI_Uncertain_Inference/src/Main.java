import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import bn.base.Assignment;
import bn.base.StringValue;
import bn.core.Distribution;
import bn.core.BayesianNetwork;
import bn.core.Inferencer;
import bn.core.RandomVariable;
import bn.inference.EnumerationInferencer;
import bn.inference.LikelihoodWeightingInferencer;
import bn.inference.RejectionSamplingInferencer;
import bn.parser.BIFParser;
import bn.parser.XMLBIFParser;
public class Main {
	public static void main(String[] argv) throws IOException, ParserConfigurationException, SAXException {
		String networkName = null;
		String filename = null;
		String prefix = "src/bn/examples/";
		
		// Creating a Bayesian Network
		BayesianNetwork bn = null;
		if(argv.length > 0 && argv[0].equals("bif")) {
			if (argv.length > 1) {
				networkName = argv[1];
				if (argv.length > 3)
					filename = prefix + argv[3];
			}
			BIFParser parser = new BIFParser(new FileInputStream(filename));
			bn = parser.parseNetwork();
		}else if(argv.length > 0 && argv[0].equals("xml")){
			if (argv.length > 1) {
				networkName = argv[1];
				if (argv.length > 3)
					filename = prefix + argv[3];
			}
			XMLBIFParser parser = new XMLBIFParser();
			bn = parser.readNetworkFromFile(filename);
		}
		
		// Creating the Query Variable
		RandomVariable X = null;
		if(argv[4] != null)
			X = bn.getVariableByName(argv[4]);
		
		// Creating the a list of observed values of the Evidence Variables
		Assignment a = new bn.base.Assignment();
		int count= 4;
		while(argv.length>++count)
			a.put(bn.getVariableByName(argv[count]), new StringValue(argv[++count]));
		
		
		// Passing the 3 Arguments onto the inferencer to obtain the distribution
		Inferencer exact = new EnumerationInferencer();
		Distribution dist1 = exact.query(X, a, bn);
		System.out.print("Inference by enumeration:\n\t"+networkName+": Distribution over "+argv[4]+" = \tP("+argv[4]+"|");
		for(int i = 5; i < argv.length; i+=2)
			System.out.print(" "+argv[i] + " = "+argv[i+1]);
		System.out.print(") = "+dist1+"\n\n");
		
		RejectionSamplingInferencer rej = new RejectionSamplingInferencer();
		Distribution dist2 = null;
		if(argv[2].equals("-"))
			dist2 = rej.query(X, a, bn);
		else 
			dist2 = rej.query(X, a, bn, Integer.parseInt(argv[2]));
		System.out.print("Rejection Sampling:\n\t"+networkName+": Distribution over "+argv[4]+" = \tP("+argv[4]+"|");
		for(int i = 5; i < argv.length; i+=2)
			System.out.print(" "+argv[i] + " = "+argv[i+1]);
		System.out.print(") = "+dist2+"\n\n");
		
		LikelihoodWeightingInferencer lik = new LikelihoodWeightingInferencer();
		Distribution dist3 = null;
		if(argv[2].equals("-"))
			dist3 = lik.query(X, a, bn);
		else 
			dist3 = lik.query(X, a, bn, Integer.parseInt(argv[2]));
		System.out.print("Likelihood Weighting:\n\t"+networkName+": Distribution over "+argv[4]+" = \tP("+argv[4]+"|");
		for(int i = 5; i < argv.length; i+=2)
			System.out.print(" "+argv[i] + " = "+argv[i+1]);
		System.out.print(") = "+dist3+"\n\n");
	}
}