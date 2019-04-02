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
		return query(X, e, network, 100000);
	}
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network, double n) {
		Distribution Q = new Distribution(X);
		double[] N= new double[X.getDomain().size()];
		int count = 0;
		for(int i = 0; i < n; i++) {
			Assignment x = new PriorSampler(network).getSample();
			if(isConsistent(x, e)) {
				int index1 = 0;
				count += 1;
				for(Value v: X.getDomain()) {
					if(v.equals(x.get(X)))
						N[index1]+=1;
					index1++;
				}
			}
		}
		int index2 = 0;
		for(Value v: X.getDomain())
			Q.set(v, (N[index2++]/count));
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