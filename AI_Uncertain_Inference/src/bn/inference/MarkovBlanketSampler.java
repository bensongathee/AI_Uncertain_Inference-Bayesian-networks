package bn.inference;

import java.util.Random;

import bn.base.Assignment;
import bn.base.BayesianNetwork;
import bn.core.RandomVariable;

public class MarkovBlanketSampler {
	
	protected BayesianNetwork network;
	 
	protected java.util.Random random;
	
	MarkovBlanketSampler(BayesianNetwork network){
		this.network = network;
		this.random = new Random();
	}
	MarkovBlanketSampler(BayesianNetwork network, long seed) {
		this.network = network;
		this.random = new Random(seed);
	}
	
	protected void sample(RandomVariable Xi, Assignment xvec) {
		
	}
}
