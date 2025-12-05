package local_search.candidate_type;

import java.security.SecureRandom;
import java.util.List;
import problem.definition.State;


public class RandomCandidate implements SearchCandidate {
    
    private static final SecureRandom random = new SecureRandom();

    @Override
    public State candidate(State stateReference, List<State> neighborhood) {
        if (neighborhood == null || neighborhood.isEmpty()) return null;
        int index = random.nextInt(neighborhood.size());
        return neighborhood.get(index);
    }
}

