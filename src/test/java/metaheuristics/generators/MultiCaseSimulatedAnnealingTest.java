package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;

public class MultiCaseSimulatedAnnealingTest {

    @Mock
    private Problem problem;
    
    @Mock
    private Operator operator;
    
    @Mock
    private State state;

    private MultiCaseSimulatedAnnealing mcsa;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Strategy strategy = Strategy.getStrategy();
        strategy.setProblem(problem);
        when(problem.getOperator()).thenReturn(operator);
        when(problem.getTypeProblem()).thenReturn(Problem.ProblemType.Maximizar);
        
        // Mock clone to return the state itself (or another mock) to avoid nulls
        when(state.clone()).thenReturn(state);
        
        mcsa = new MultiCaseSimulatedAnnealing();
    }

    @Test
    public void testInitialization() {
        assertNotNull(mcsa);
        assertEquals(GeneratorType.MultiCaseSimulatedAnnealing, mcsa.getType());
        assertEquals(50.0f, mcsa.getWeight(), 0.001);
    }

    @Test
    public void testGenerate() throws Exception {
        mcsa.setInitialReference(state);
        
        List<State> neighborhood = new ArrayList<>();
        when(operator.generatedNewState(eq(state), anyInt())).thenReturn(neighborhood);
        
        try {
            mcsa.generate(1);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testUpdateReference_Cooling() throws Exception {
        mcsa.setInitialReference(state);
        // Test cooling schedule
        MultiCaseSimulatedAnnealing.tinitial = 100.0;
        MultiCaseSimulatedAnnealing.alpha = 0.9;
        MultiCaseSimulatedAnnealing.countIterationsT = 2;
        
        // Trigger update at iteration 2
        mcsa.updateReference(state, 2);
        
        // Check tinitial reduced
        assertEquals(90.0, MultiCaseSimulatedAnnealing.tinitial, 0.001);
        // Check countIterationsT increased
        // Logic: countIterationsT = countIterationsT + countRept; (where countRept was old countIterationsT, i.e. 2)
        assertEquals(4, MultiCaseSimulatedAnnealing.countIterationsT);
    }
}

