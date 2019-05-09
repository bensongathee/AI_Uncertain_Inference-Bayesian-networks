package bn.inference;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.base.Distribution;
import bn.core.Inferencer;
import bn.core.RandomVariable;
import bn.core.Value;
import bn.inference.WeightedSampler.Sample;
public class LikelihoodWeightingInferencer extends java.lang.Object implements Inferencer{

	@Override
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
		return query(X, e, network, 100000);
	}
	
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network, int n) {
		Distribution Q = new Distribution(X);
		double[] W = new double[X.getDomain().size()];
		double totalWeight = 0;
		
		for(int i = 0; i < n; i++) {
			Sample sample = new WeightedSampler(network).getSample(e);
			int index1 = 0;
			for(Value v: X.getDomain()) {
				if(v.equals(sample.e.get(X)))
					W[index1] += sample.weight;
				index1++;
			}
			totalWeight += sample.weight;
		}
		int index2 = 0;
		for(Value v: X.getDomain())
			Q.set(v, (W[index2++]/totalWeight));
		Q.normalize();
		return Q;
	}
}
