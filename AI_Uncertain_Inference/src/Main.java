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
import bn.parser.XMLBIFPrinter;
public class Main {
	public static void main(String[] argv) throws IOException, ParserConfigurationException, SAXException {
		String filename = null;
		String networkName = null;
		
		// Creating a Bayesian Network
		BayesianNetwork bn = null;
		if(argv.length > 0 && argv[0].equals("bif")) {
			if (argv.length > 1) {
				filename = argv[1];
				if (argv.length > 2)
					networkName = argv[2];
			}
			BIFParser parser = new BIFParser(new FileInputStream(filename));
			bn = parser.parseNetwork();
		}else if(argv.length > 0 && argv[0].equals("xml")){
			if (argv.length > 1) {
				filename = argv[1];
				if (argv.length > 2)
					networkName = argv[2];
			}
			XMLBIFParser parser = new XMLBIFParser();
			bn = parser.readNetworkFromFile(filename);
		}
		
		// Creating the Query Variable
		RandomVariable X = null;
		if(argv[3] != null)
			X = bn.getVariableByName(argv[3]);
		
		// Creating the a list of observed values of the Evidence Variables
		Assignment a = new bn.base.Assignment();
		int count= 3;
		while(argv.length>++count) {
			a.put(bn.getVariableByName(argv[count]), new StringValue("true"));
			if(argv[++count].equals("false"))
				a.put(bn.getVariableByName(argv[count-1]), new StringValue("false"));
		}
		
		// Passing the 3 Arguments onto the inferencer to obtain the distribution
		Inferencer exact = new EnumerationInferencer();
		Distribution dist1 = exact.query(X, a, bn);
		if(argv.length>6)
			System.out.println(networkName+" distribution:\tP("+argv[3]+"|"+argv[4]+" = "+argv[5]+","+argv[6]+" = "+argv[7]+") = "+dist1);
		else if(argv.length>4)
			System.out.println(networkName+" distribution:\tP("+argv[3]+"|"+argv[4]+" = "+argv[5]+") = "+dist1);
		else if(argv.length>2)
			System.out.println(networkName+" distribution:\tP("+argv[3]+") = "+dist1);
		
		Inferencer rej = new RejectionSamplingInferencer();
		Distribution dist2 = rej.query(X, a, bn);
		if(argv.length>6)
			System.out.println(networkName+" distribution:\tP("+argv[3]+"|"+argv[4]+" = "+argv[5]+","+argv[6]+" = "+argv[7]+") = "+dist2);
		else if(argv.length>4)
			System.out.println(networkName+" distribution:\tP("+argv[3]+"|"+argv[4]+" = "+argv[5]+") = "+dist2);
		else if(argv.length>2)
			System.out.println(networkName+" distribution:\tP("+argv[3]+") = "+dist2);
		
		Inferencer lik = new LikelihoodWeightingInferencer();
		Distribution dist3 = lik.query(X, a, bn);
		if(argv.length>6)
			System.out.println(networkName+" distribution:\tP("+argv[3]+"|"+argv[4]+" = "+argv[5]+","+argv[6]+" = "+argv[7]+") = "+dist3);
		else if(argv.length>4)
			System.out.println(networkName+" distribution:\tP("+argv[3]+"|"+argv[4]+" = "+argv[5]+") = "+dist3);
		else if(argv.length>2)
			System.out.println(networkName+" distribution:\tP("+argv[3]+") = "+dist3);
	}
}