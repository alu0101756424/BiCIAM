package local_search.acceptation_type;

import problem.definition.State;

public class AcceptAnyone implements AcceptableCandidate {
    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        return true;
    }
}

