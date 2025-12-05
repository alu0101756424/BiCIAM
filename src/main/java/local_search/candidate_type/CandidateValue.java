package local_search.candidate_type;

import java.util.List;
import local_search.complement.StrategyType;
import local_search.complement.TabuSolutions;
import problem.definition.State;

public class CandidateValue {

    private TabuSolutions tabuSolutions;

    public CandidateValue() {
        this.tabuSolutions = new TabuSolutions();
    }

    public State stateCandidate(State stateReference, CandidateType type, StrategyType strategy, Integer operatornumber, List<State> neighborhood) {
        
        // Filter neighborhood if Tabu
        List<State> filteredNeighborhood = neighborhood;
        try {
            if (tabuSolutions != null) {
                filteredNeighborhood = tabuSolutions.filterNeighbor(neighborhood);
            }
        } catch (Exception e) {
            
        }
        
        if (filteredNeighborhood.isEmpty()) {
            return null;
        }

        switch (type) {
            case RandomCandidate:
                return new RandomCandidate().candidate(stateReference, filteredNeighborhood);
            case GreaterCandidate:
                return new GreaterCandidate().candidate(stateReference, filteredNeighborhood);
            case SmallerCandidate:
                return new SmallerCandidate().candidate(stateReference, filteredNeighborhood);
            case NotDominatedCandidate:
                return new NotDominatedCandidate().candidate(stateReference, filteredNeighborhood);
            default:
                return null;
        }
    }
}
