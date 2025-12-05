package local_search.acceptation_type;

import problem.definition.State;

public class AcceptNotBadU implements AcceptableCandidate {

    public AcceptNotBadU() {
    }

    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        return true;
    }
}

