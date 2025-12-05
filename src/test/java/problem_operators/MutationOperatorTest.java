package problem_operators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheurictics.strategy.Strategy;
import problem.definition.Codification;
import problem.definition.Problem;
import problem.definition.State;

public class MutationOperatorTest {

    @Mock
    private Problem problem;

    @Mock
    private Codification codification;

    @Mock
    private State stateCurrent;

    private MutationOperator mutationOperator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup Strategy singleton
        Strategy strategy = Strategy.getStrategy();
        strategy.setProblem(problem);
        
        // Setup Problem mocks
        when(problem.getCodification()).thenReturn(codification);
        
        mutationOperator = new MutationOperator();
    }

    @Test
    public void testGeneratedNewState() {
        int operatorNumber = 3;
        int aleatoryKey = 1;
        Object aleatoryValue = "newValue";
        
        // Mock Codification behavior
        when(codification.getAleatoryKey()).thenReturn(aleatoryKey);
        when(codification.getVariableAleatoryValue(aleatoryKey)).thenReturn(aleatoryValue);
        
        // Mock State behavior
        State stateCopy = mock(State.class);
        ArrayList<Object> code = new ArrayList<>();
        code.add("oldValue");
        code.add("oldValue2");
        
        when(stateCurrent.getCopy()).thenReturn(stateCopy);
        when(stateCopy.getCode()).thenReturn(code);
        
        // Execute
        List<State> result = mutationOperator.generatedNewState(stateCurrent, operatorNumber);
        
        // Verify
        assertNotNull(result);
        assertEquals(operatorNumber, result.size());
        
        // Verify interactions
        verify(codification, times(operatorNumber)).getAleatoryKey();
        verify(codification, times(operatorNumber)).getVariableAleatoryValue(aleatoryKey);
        verify(stateCurrent, times(operatorNumber)).getCopy();
        
        // Verify state modification
        // Since we use the same list for all copies in this mock setup, checking one is enough
        assertEquals(aleatoryValue, code.get(aleatoryKey));
    }

    @Test
    public void testGenerateRandomState() {
        List<State> result = mutationOperator.generateRandomState(5);
        assertNull(result);
    }
}
