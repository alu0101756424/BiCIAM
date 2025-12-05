package local_search.acceptation_type;

import problem.definition.State;

public interface AcceptableCandidate {
    Boolean acceptCandidate(State stateCurrent, State stateCandidate);
}

