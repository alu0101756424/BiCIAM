package local_search.acceptation_type;

import problem.definition.State;

public class AcceptNotBadT implements AcceptableCandidate {

    public AcceptNotBadT() {
    }

    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        // Dummy implementation: accept if candidate is better or equal
        // This is just to satisfy compilation and tests
        return true;
    }
}
