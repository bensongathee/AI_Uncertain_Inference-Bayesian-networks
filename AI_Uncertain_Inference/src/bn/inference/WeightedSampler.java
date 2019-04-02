package bn.inference;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.RandomVariable;

public class WeightedSampler extends PriorSampler{

	public class Sample {
		public Assignment e = new bn.base.Assignment();
		public double weight = 1;
	}
	
	public WeightedSampler(BayesianNetwork network) {
		super(network);
	}

	public WeightedSampler(BayesianNetwork network, long seed) {
		super(network);
	}
	
	public WeightedSampler.Sample getSample(Assignment x) {
		Sample sample = new Sample();
		for(RandomVariable v : network.getVariablesSortedTopologically()) {
			if(x.containsKey(v)) {
				sample.e.put(v, x.get(v));
				sample.weight *= network.getProbability(v, sample.e);
			}
			else
				sample.e.put(v, randomSampleForVariable(v, sample.e));
		}
		return sample;
	}
}
