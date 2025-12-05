package local_search.acceptation_type;

import local_search.complement.TabuSolutions;
import metaheuristics.strategy.Strategy;
import problem.definition.State;

public class AcceptNotDominatedTabu implements AcceptableCandidate {

    private Dominance dominance;
    private TabuSolutions tabuSolutions;

    public AcceptNotDominatedTabu() {
        this.dominance = new Dominance();
        this.tabuSolutions = new TabuSolutions();
    }

    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        // Check if candidate is in Tabu list
        if (TabuSolutions.listTabu != null) {
            for (State tabu : TabuSolutions.listTabu) {
                if (stateCandidate.Comparator(tabu)) {
                    return false; // Reject if tabu
                }
            }
        }
        
        // Check dominance
        return !dominance.dominance(stateCurrent, stateCandidate);
    }
}

