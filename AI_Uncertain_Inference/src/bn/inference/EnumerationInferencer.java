package bn.inference;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Inferencer;
import bn.core.RandomVariable;
import bn.core.Value;

public class EnumerationInferencer extends java.lang.Object implements Inferencer{
	@Override
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork bn) {
		return enumerateAsk(X, e, bn);
	}
	public Distribution enumerateAsk(RandomVariable X,Assignment e,BayesianNetwork bn) {
		Distribution Q = new bn.base.Distribution(X);
		for(Value x : X.getDomain()) {
			Q.set(x, enumerateAll(bn.getVariablesSortedTopologically(), e, bn));
		}
		Q.normalize();
		return Q;
	}
	protected double enumerateAll(java.util.List<RandomVariable> vars, Assignment e, BayesianNetwork bn) {
		if(vars.isEmpty())
			return 1;
		RandomVariable Y;
//		if() {
//			
//		}else {
//			
//		}
		return 0;
	}
	
}