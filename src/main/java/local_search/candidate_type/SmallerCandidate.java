package local_search.candidate_type;

import java.util.List;
import problem.definition.State;

public class SmallerCandidate implements SearchCandidate {
    @Override
    public State candidate(State stateReference, List<State> neighborhood) {
        if (neighborhood == null || neighborhood.isEmpty()) return null;
        State best = neighborhood.get(0);
        for (State s : neighborhood) {
            if (s.getEvaluation().get(0) < best.getEvaluation().get(0)) {
                best = s;
            }
        }
        return best;
    }
}