import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import bn.base.Assignment;
import bn.base.BooleanValue;
import bn.core.Distribution;
import bn.core.BayesianNetwork;
import bn.core.Inferencer;
import bn.core.RandomVariable;
import bn.inference.EnumerationInferencer;
import bn.parser.BIFParser;
import bn.parser.XMLBIFParser;
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
		
		a.put(bn.getVariableByName(argv[4]), BooleanValue.TRUE);
		if(argv[5].equals("false"))
			a.put(bn.getVariableByName(argv[4]), BooleanValue.FALSE);
		
		a.put(bn.getVariableByName(argv[6]), BooleanValue.TRUE);
		if(argv[5].equals("false"))
			a.put(bn.getVariableByName(argv[6]), BooleanValue.FALSE);
		
		// Passing the 3 Arguments onto the inferencer to obtain the distribution
		Inferencer exact = new EnumerationInferencer();
		Distribution dist = exact.query(X, a, bn);
		System.out.println(networkName+" distribution:\tP("+argv[3]+"|"+argv[4]+","+argv[6]+") = "+dist);
	}
}