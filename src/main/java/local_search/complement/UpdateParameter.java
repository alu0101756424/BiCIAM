package local_search.complement;

import factory_method.FactoryLoader;
import metaheuristics.generators.DistributionEstimationAlgorithm;
import metaheuristics.generators.EvolutionStrategies;
import metaheuristics.generators.Generator;
import metaheuristics.generators.GeneticAlgorithm;
import metaheuristics.generators.ParticleSwarmOptimization;
import metaheuristics.strategy.Strategy;

public class UpdateParameter {
    
    public UpdateParameter() {
    }

    public static Integer updateParameter(Integer parameter) {
        Integer countIterationsCurrent = parameter + 1;
        Generator generator = null;
        try {
            if (countIterationsCurrent == GeneticAlgorithm.countRef - 1) {
                generator = (Generator) FactoryLoader.getInstance("metaheuristics.generators.GeneticAlgorithm");
            } else if (countIterationsCurrent == EvolutionStrategies.countRef - 1) {
                generator = (Generator) FactoryLoader.getInstance("metaheuristics.generators.EvolutionStrategies");
            } else if (countIterationsCurrent == DistributionEstimationAlgorithm.countRef - 1) {
                generator = (Generator) FactoryLoader.getInstance("metaheuristics.generators.DistributionEstimationAlgorithm");
            } else if (countIterationsCurrent == ParticleSwarmOptimization.countRef - 1) {
                generator = (Generator) FactoryLoader.getInstance("metaheuristics.generators.ParticleSwarmOptimization");
            }
            
            if (generator != null) {
                Strategy.getStrategy().generator = generator;
            }
        } catch (Exception e) {
            
        }
        return countIterationsCurrent;
    }
}
