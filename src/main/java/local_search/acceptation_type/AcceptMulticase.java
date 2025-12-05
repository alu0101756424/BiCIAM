package local_search.acceptation_type;

import problem.definition.State;

public class AcceptMulticase implements AcceptableCandidate {

    public AcceptMulticase() {
    }

    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        return true;
    }
}
