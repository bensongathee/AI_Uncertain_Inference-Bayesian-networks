package bn.inference;
import java.util.Random;
import bn.base.Assignment;
import bn.core.BayesianNetwork;
import bn.core.RandomVariable;
import bn.core.Value;
public class PriorSampler extends java.lang.Object{
	protected BayesianNetwork network;
	protected java.util.List<RandomVariable> variables;
	protected java.util.Random random;
	public PriorSampler(BayesianNetwork network) {
		this.network = network;
		this.random = new Random();
	} 
	public PriorSampler(BayesianNetwork network,long seed) {
		this.network = network;
		this.random = new Random(seed);
	}
	public Assignment getSample() {
		Assignment x = new Assignment();
		variables = network.getVariablesSortedTopologically();
		for(RandomVariable v : variables)
			x.put(v,randomSampleForVariable(v, x));
		return x;
	}
	protected Value randomSampleForVariable(RandomVariable Xi,Assignment x) {
		Value v = null;
		double sum=0;
		double ran = random.nextDouble();
		for(Value xi : Xi.getDomain()) {
			x.put(Xi, xi);
			sum+=network.getProbability(Xi, x);
			if(ran < sum) {
				v = xi;
				x.remove(Xi);
				break;
			}
			x.remove(Xi);
		}
		return v;
	}
}