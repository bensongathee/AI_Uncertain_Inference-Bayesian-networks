package bn.inference;

import bn.base.Assignment;
import bn.core.BayesianNetwork;
import bn.core.RandomVariable;

public class WeightedSampler extends PriorSampler{

	public class Sample {
		public Assignment e;
		public double weight; 
		
		public String toString(){
			return "Assignment = " + e.toString() + ", Weight = " + weight;
		}
	}
	
	public WeightedSampler(BayesianNetwork network) {
		super(network);
	}

	public WeightedSampler(BayesianNetwork network, long seed) {
		super(network);
	}
	
	public WeightedSampler.Sample getSample(Assignment x) {
		Sample sample = new Sample();
		sample.e = x;
		for(RandomVariable v : network.getVariablesSortedTopologically()) {
			if(x.containsKey(v))
				sample.weight *= network.getProbability(v, x);
			else
				sample.e.put(v, randomSampleForVariable(v, sample.e));
		}
		return sample;
	}
}
