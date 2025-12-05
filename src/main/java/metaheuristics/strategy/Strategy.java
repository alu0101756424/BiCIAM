package metaheuristics.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import metaheuristics.generators.Generator;
import metaheuristics.generators.GeneratorType;
import problem.definition.Problem;
import problem.definition.State;

public class Strategy {
    
    private static Strategy strategy;
    private Problem problem;
    private int countMax;
    private int countCurrent;
    
    public List<State> listRefPoblacFinal = new ArrayList<>();
    public List<State> listBest = new ArrayList<>();
    public Map<GeneratorType, Generator> mapGenerators = new HashMap<>();
    private List<String> listKey = new ArrayList<>();
    
    public Generator generator;
    public List<State> listStates;

    private Strategy() {
    }

    public static Strategy getStrategy() {
        if (strategy == null) {
            strategy = new Strategy();
        }
        return strategy;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public int getCountMax() {
        return countMax;
    }

    public void setCountMax(int countMax) {
        this.countMax = countMax;
    }

    public int getCountCurrent() {
        return countCurrent;
    }

    public void setCountCurrent(int countCurrent) {
        this.countCurrent = countCurrent;
    }
    
    public List<String> getListKey() {
        return listKey;
    }
    
    public void setListKey(List<String> listKey) {
        this.listKey = listKey;
    }
    
    public State getBestState() {
        if (listBest != null && !listBest.isEmpty()) {
            return listBest.get(0);
        }
        return null;
    }
    
    // Helper to reset singleton for testing
    public static void reset() {
        strategy = null;
    }
}

