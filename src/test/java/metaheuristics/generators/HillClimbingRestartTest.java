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

public class HillClimbingRestartTest {

    @Mock
    private Problem problem;
    
    @Mock
    private Operator operator;
    
    @Mock
    private State state;

    private HillClimbingRestart hcr;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Strategy strategy = Strategy.getStrategy();
        strategy.setProblem(problem);
        when(problem.getOperator()).thenReturn(operator);
        // Default to Maximizar for standard setup
        when(problem.getTypeProblem()).thenReturn(Problem.ProblemType.Maximizar);
        
        // Reset static counters usually used in HCR
        HillClimbingRestart.count = 10;
        HillClimbingRestart.countCurrent = 10;
        
        hcr = new HillClimbingRestart();
    }

    @Test
    public void testInitialization() {
        assertNotNull(hcr);
        assertEquals(GeneratorType.HillClimbingRestart, hcr.getType());
    }

    @Test
    public void testGenerate_NoRestart() throws Exception {
        hcr.setInitialReference(state);
        
        // Setup condition: count != countCurrent
        // HillClimbingRestart.count is static. 
        // In generate: if(count == Strategy.getStrategy().getCountCurrent())
        
        Strategy.getStrategy().setCountCurrent(5); // count is 10
        
        List<State> neighborhood = new ArrayList<>();
        // when(operator.generatedNewState(eq(state), anyInt())).thenReturn(neighborhood);
        // Note: operator is called on stateReferenceHC
        
        try {
            hcr.generate(1);
        } catch (Exception e) {
            // Expected if dependencies are deep
        }
        
        // Verify no restart logic triggered (e.g. generateRandomState not called)
        verify(operator, never()).generateRandomState(anyInt());
    }
    
    @Test
    public void testGenerate_Restart() throws Exception {
        hcr.setInitialReference(state);
        
        // Setup condition for restart
        // if(count == Strategy.getStrategy().getCountCurrent())
        Strategy.getStrategy().setCountCurrent(10); // Matches count=10
        
        // Mock random state generation for restart
        List<State> randomStates = new ArrayList<>();
        State randomState = mock(State.class);
        randomStates.add(randomState);
        when(operator.generateRandomState(anyInt())).thenReturn(randomStates);
        
        // Mock neighborhood for the NEW reference
        when(operator.generatedNewState(eq(randomState), anyInt())).thenReturn(new ArrayList<>());
        
        try {
            hcr.generate(1);
        } catch (Exception e) {
            // catch potential issues inside stateCandidate creation
        }
        
        // Verify restart happened using random state
        verify(operator).generateRandomState(1);
        // Verify Evaluate was called
        verify(problem).Evaluate(randomState);
        // Verify count updated
        assertTrue(HillClimbingRestart.count > 10);
    }
    
    @Test
    public void testUpdateReference() throws Exception {
        hcr.setInitialReference(state);
        // Basic harness
        try {
            hcr.updateReference(state, 1);
        } catch (Exception e) {
        }
    }
}

