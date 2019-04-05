package bn.inference;
import java.util.ArrayList;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Inferencer;
import bn.core.RandomVariable;
import bn.core.Value;
public class EnumerationInferencer implements Inferencer{
	@Override
	public Distribution query(RandomVariable X, Assignment e, BayesianNetwork bn) {
		return enumerateAsk(X, e, bn);
	}
	public Distribution enumerateAsk(RandomVariable X,Assignment e,BayesianNetwork bn) {
		Distribution Q = new bn.base.Distribution(X);
		for(Value x : X.getDomain()) {
			e.put(X, x);
			Q.set(x, enumerateAll(bn.getVariablesSortedTopologically(), e, bn));
			e.remove(X);
		}
		Q.normalize();
		return Q;
	}
	protected double enumerateAll(java.util.List<RandomVariable> vars, Assignment e, BayesianNetwork bn) {
		if(vars.isEmpty())
			return 1; 
		
		RandomVariable Y = vars.get(0);
		vars.remove(0);
		
		if(e.containsKey(Y))
			return bn.getProbability(Y, e) * enumerateAll(new ArrayList<>(vars), e, bn);
		
		double sum = 0;
		for(Value y : Y.getDomain()) {
			e.put(Y, y);
			sum += bn.getProbability(Y, e) * enumerateAll(new ArrayList<>(vars), e, bn);
			e.remove(Y);
		}
		return sum;
	}
}