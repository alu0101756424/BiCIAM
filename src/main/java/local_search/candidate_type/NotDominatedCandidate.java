package local_search.candidate_type;

import java.util.List;
import local_search.acceptation_type.Dominance;
import problem.definition.State;

public class NotDominatedCandidate implements SearchCandidate {
    
    private Dominance dominance;
    
    public NotDominatedCandidate() {
        this.dominance = new Dominance();
    }
    
    @Override
    public State candidate(State stateReference, List<State> neighborhood) {
        if (neighborhood == null || neighborhood.isEmpty()) return null;
        
        for (State s : neighborhood) {
            // Return first candidate that is NOT dominated by reference
            if (!dominance.dominance(stateReference, s)) {
                return s;
            }
        }
        // If all are dominated, return first one? Or null?
        // Returning first one as fallback
        return neighborhood.get(0);
    }
}

