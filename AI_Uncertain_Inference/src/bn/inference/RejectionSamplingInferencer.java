package bn.inference;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.base.Distribution;
import bn.core.Inferencer;
import bn.core.RandomVariable;
import bn.core.Value;
public class RejectionSamplingInferencer extends java.lang.Object implements Inferencer{
	@Override
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
		return query(X, e, network, 1000);
	}
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network, double n) {
		Distribution Q = new Distribution(X);
		double[] arr= new double[X.getDomain().size()];
		for(int i = 0; i < n; i++) {
			Assignment x = new PriorSampler(network).getSample();
			if(isConsistent(x, e)) {
				int index = 0;
				for(Value v: X.getDomain()) {
					if(v.equals(x.get(X)))
						arr[index]+=1;
					Q.set(v, (arr[index++]/n));
				}
			}
		}
		Q.normalize();
		return Q;	
	}
	protected boolean isConsistent(Assignment x, Assignment e) {
		for(RandomVariable v : e.keySet()) 
			for(RandomVariable xi : x.keySet())
				if(v.equals(xi))
					if(!(e.get(v).equals(x.get(xi))))
						return false;
		return true;
	}
}