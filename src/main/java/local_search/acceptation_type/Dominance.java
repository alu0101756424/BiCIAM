package local_search.acceptation_type;

import java.util.List;
import metaheuristics.strategy.Strategy;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

public class Dominance {

    public Dominance() {
    }

    public boolean dominance(State solutionX, State solutionY) {
        boolean dominate = false;
        boolean betterInOne = false;
        boolean worseInNone = true;
        
        List<Double> evalX = solutionX.getEvaluation();
        List<Double> evalY = solutionY.getEvaluation();
        
        ProblemType type = Strategy.getStrategy().getProblem().getTypeProblem();
        
        for (int i = 0; i < evalX.size(); i++) {
            double valX = evalX.get(i);
            double valY = evalY.get(i);
            
            if (type == ProblemType.Maximizar) {
                if (valX < valY) {
                    worseInNone = false;
                    break;
                }
                if (valX > valY) {
                    betterInOne = true;
                }
            } else { // Minimizar
                if (valX > valY) {
                    worseInNone = false;
                    break;
                }
                if (valX < valY) {
                    betterInOne = true;
                }
            }
        }
        
        if (worseInNone && betterInOne) {
            dominate = true;
        }
        return dominate;
    }

    public boolean ListDominance(State stateCandidate, List<State> listRefPoblacFinal) {
        for (State ref : listRefPoblacFinal) {
            if (dominance(ref, stateCandidate)) {
                return true; // Candidate is dominated by at least one reference
            }
        }
        return false;
    }
}

