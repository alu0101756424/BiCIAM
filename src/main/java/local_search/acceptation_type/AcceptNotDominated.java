package local_search.acceptation_type;

import metaheurictics.strategy.Strategy;
import problem.definition.State;

public class AcceptNotDominated implements AcceptableCandidate {

    private Dominance dominance;

    public AcceptNotDominated() {
        this.dominance = new Dominance();
    }

    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        // Accept if candidate is NOT dominated by current
        return !dominance.dominance(stateCurrent, stateCandidate);
    }
}
