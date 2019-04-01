package bn.inference;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.base.Distribution;
import bn.core.Inferencer;
import bn.core.RandomVariable;
public class LikelihoodWeightingInferencer extends java.lang.Object implements Inferencer{

	@Override
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
		return query(X, e, network, 1000);
	}
	
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network, int n) {
		Distribution Q = new Distribution(X);
		
		return Q;
	}
}
