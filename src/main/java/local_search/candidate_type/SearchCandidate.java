package local_search.candidate_type;

import java.util.List;
import problem.definition.State;

public interface SearchCandidate {
    State candidate(State stateReference, List<State> neighborhood);
}

