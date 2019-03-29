package bn.inference;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Inferencer;
import bn.core.RandomVariable;

public class EnumerationInferencer extends java.lang.Object implements Inferencer{

	@Override
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Distribution enumerateAsk(RandomVariable X,Assignment e,BayesianNetwork bn) {
		return null;
		
	}
	
	protected double enumerateAll(java.util.List<RandomVariable> vars, Assignment e, BayesianNetwork bn) {
		return 0;
		
	}
	
}