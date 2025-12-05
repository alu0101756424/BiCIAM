package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheurictics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

public class MultiGeneratorTest {

    @Mock
    private Problem problem;

    @Mock
    private State state;

    private MultiGenerator multiGenerator;

    @BeforeEach
    public void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
            Strategy strategy = Strategy.getStrategy();
            strategy.setProblem(problem);
            
            // Ensure mapGenerators is initialized
            if (strategy.mapGenerators == null) {
                strategy.mapGenerators = new java.util.TreeMap<>();
            }
            
            // Populate mapGenerators with mocks to satisfy dependencies (e.g. EvolutionStrategies)
            strategy.mapGenerators.put(GeneratorType.EvolutionStrategies, mock(EvolutionStrategies.class));
            strategy.mapGenerators.put(GeneratorType.GeneticAlgorithm, mock(GeneticAlgorithm.class));
            strategy.mapGenerators.put(GeneratorType.HillClimbing, mock(HillClimbing.class));
            strategy.mapGenerators.put(GeneratorType.RandomSearch, mock(RandomSearch.class));
            
            multiGenerator = new MultiGenerator();
            
            // Reset static state
            MultiGenerator.destroyMultiGenerator();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @AfterEach
    public void tearDown() {
        MultiGenerator.destroyMultiGenerator();
    }

    @Test
    public void testInitialization() {
        assertNotNull(multiGenerator);
        assertEquals(GeneratorType.MultiGenerator, multiGenerator.getType());
    }

    @Test
    public void testRoulette() throws Exception {
        // Mock generators
        Generator g1 = mock(Generator.class);
        Generator g2 = mock(Generator.class);
        
        when(g1.getWeight()).thenReturn(10.0f);
        when(g2.getWeight()).thenReturn(90.0f);
        
        Generator[] generators = new Generator[] { g1, g2 };
        MultiGenerator.setListGenerators(generators);
        
        Generator selected = multiGenerator.roulette();
        assertNotNull(selected);
        assertTrue(selected == g1 || selected == g2);
    }

    @Test
    public void testGenerate() throws Exception {
        // Setup generators
        Generator mockGen = mock(Generator.class);
        when(mockGen.getWeight()).thenReturn(100.0f); // Ensure it's picked
        when(mockGen.generate(anyInt())).thenReturn(state);
        
        Generator[] generators = new Generator[] { mockGen };
        MultiGenerator.setListGenerators(generators);
        
        // Execute
        State result = multiGenerator.generate(1);
        
        // Verify
        assertEquals(state, result);
        assertEquals(mockGen, MultiGenerator.getActiveGenerator());
        assertEquals(mockGen, Strategy.getStrategy().generator);
        verify(mockGen).generate(1);
    }

/*
    @Test
    public void testUpdateReference_Improvement() throws Exception {
        // Setup active generator
        Generator mockGen = mock(Generator.class);
        when(mockGen.getWeight()).thenReturn(50.0f);
        when(mockGen.getTrace()).thenReturn(new float[100]); // Avoid NPE in updateAward
        when(mockGen.getType()).thenReturn(GeneratorType.HillClimbing);
        MultiGenerator.setActiveGenerator(mockGen);
        
        Generator[] generators = new Generator[] { mockGen };
        MultiGenerator.setListGenerators(generators);
        
        // Setup Strategy best state for comparison
        State bestState = mock(State.class);
        ArrayList<Double> bestEval = new ArrayList<>();
        bestEval.add(10.0);
        when(bestState.getEvaluation()).thenReturn(bestEval);
        
        // Use reflection to set bestState and countCurrent
        java.lang.reflect.Field bestStateField = Strategy.class.getDeclaredField("bestState");
        bestStateField.setAccessible(true);
        bestStateField.set(Strategy.getStrategy(), bestState);
        
        java.lang.reflect.Field countCurrentField = Strategy.class.getDeclaredField("countCurrent");
        countCurrentField.setAccessible(true);
        countCurrentField.set(Strategy.getStrategy(), 0);
        
        // Setup candidate state (better than best)
        State candidate = mock(State.class);
        ArrayList<Double> candEval = new ArrayList<>();
        candEval.add(20.0); // Higher is better for Maximization
        when(candidate.getEvaluation()).thenReturn(candEval);
        
        when(problem.getTypeProblem()).thenReturn(problem.definition.Problem.ProblemType.Maximizar);
        
        // Execute
        multiGenerator.updateReference(candidate, 1);
        
        // Verify weight increased (updateAwardSC)
        verify(mockGen).setWeight(gt(50.0f));
    }
*/
}
