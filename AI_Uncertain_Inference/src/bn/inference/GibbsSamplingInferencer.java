package bn.inference;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.base.Distribution;
import bn.core.Inferencer;
import bn.core.RandomVariable;

public class GibbsSamplingInferencer extends java.lang.Object implements Inferencer{

	@Override
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
		return query(X, e, network, 1000);
	}
	
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork bn, int n) {
		Distribution Q = new Distribution(X);
		
		for(int i = 0; i < n; i++) {
			
		}
		
		return Q;
	}

}
