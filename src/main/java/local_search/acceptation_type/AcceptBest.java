package local_search.acceptation_type;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

public class AcceptBest implements AcceptableCandidate {
    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        ProblemType type = Strategy.getStrategy().getProblem().getTypeProblem();
        double valCurrent = stateCurrent.getEvaluation().get(0);
        double valCandidate = stateCandidate.getEvaluation().get(0);
        
        if (type == ProblemType.Maximizar) {
            return valCandidate > valCurrent;
        } else {
            return valCandidate < valCurrent;
        }
    }
}

