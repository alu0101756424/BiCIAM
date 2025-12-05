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

public class MultiobjectiveStochasticHillClimbingTest {

    @Mock
    private Problem problem;
    
    @Mock
    private Operator operator;
    
    @Mock
    private State state;

    private MultiobjectiveStochasticHillClimbing mshc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Strategy strategy = Strategy.getStrategy();
        strategy.setProblem(problem);
        when(problem.getOperator()).thenReturn(operator);
        when(problem.getTypeProblem()).thenReturn(Problem.ProblemType.Maximizar);
        
        when(state.clone()).thenReturn(state);
        
        Strategy.getStrategy().listRefPoblacFinal = new ArrayList<>();
        Strategy.getStrategy().listRefPoblacFinal.add(state);

        mshc = new MultiobjectiveStochasticHillClimbing();
    }

    @Test
    public void testInitialization() {
        assertNotNull(mshc);
        assertEquals(GeneratorType.MultiobjectiveStochasticHillClimbing, mshc.getType());
    }

    @Test
    public void testGenerate() throws Exception {
        mshc.setInitialReference(state);
        List<State> neighborhood = new ArrayList<>();
        when(operator.generatedNewState(eq(state), anyInt())).thenReturn(neighborhood);
        
        try {
            mshc.generate(1);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testUpdateReference() throws Exception {
        mshc.setInitialReference(state);
        mshc.updateReference(state, 1);
        assertNotNull(mshc.getReference());
    }
}

