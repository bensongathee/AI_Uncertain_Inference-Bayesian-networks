# AI_Uncertain_Inference-Bayesian-networks
Uncertain inference, focusing on Bayesian networks which are also the basis for many other formalisms used in AI for dealing with uncertain knowledge

# Configurations
If running on Eclipse, (Run Configurations) and provide the argument similar to example listed below:

    xml AIMA-Alarm 10000 src/bn/examples/aima-alarm.xml B J true M true

or

    xml AIMA-Alarm 10000 src/bn/examples/aima-alarm.xml B J true M true

where,

B is the query variable,

J & M are the evidence variables with their values written right after,

the number 100000 (equal to the number of samples generated in prior and weighted samplers) can be replaced by a single '-' (without the '') to run the default 100000 number of samples.

eg.:

    xml AIMA-Alarm - src/bn/examples/aima-alarm.xml B J true M true

Note- it works for any bayesian network (though very slowly for ones with very high number of variables), even those having variables with domain sizes greater than 2, and non boolean.

Note- If the number of samples is very small, rejection sampling may reject all samples, especially in cases such as the burglary example, as the probability of evidence matching is extremely low.

Note- Number of evidence variables can range from 0 to a reasonable number.

# Preview

java -cp "./bin" Main xml AIMA-Alarm - src/bn/examples/aima-alarm.xml B J true M true

Inference by enumeration:

        AIMA-Alarm: Distribution over B =       P(B| J = true M = true) = {true=0.2841718353643929, false=0.7158281646356071}

Rejection Sampling:

        AIMA-Alarm: Distribution over B =       P(B| J = true M = true) = {true=0.20657276995305165, false=0.7934272300469484}

Likelihood Weighting:

        AIMA-Alarm: Distribution over B =       P(B| J = true M = true) = {true=0.29853390710010325, false=0.7014660928998967}
