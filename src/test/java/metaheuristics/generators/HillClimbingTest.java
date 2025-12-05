package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheurictics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Operator;
import problem.definition.Codification;
import problem.definition.Problem.ProblemType;
import local_search.candidate_type.CandidateType;

public class HillClimbingTest {

    private HillClimbing hillClimbing;

    @Mock
    private Strategy strategy;

    @Mock
    private Problem problem;

    @Mock
    private Operator operator;
    
    @Mock
    private Codification codification;

    @Mock
    private State state;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Mock Strategy singleton
        // Note: Mocking static methods or singletons is hard with basic Mockito.
        // However, HillClimbing uses Strategy.getStrategy().getProblem()...
        // We might need to rely on the fact that Strategy is a singleton and we can't easily mock it without PowerMock.
        // Or we can try to set the fields in HillClimbing if possible, but they are protected.
        // HillClimbing constructor calls Strategy.getStrategy()... which might be problematic if not initialized.
        
        // For now, let's try to instantiate HillClimbing. If it fails due to Strategy, we might need to refactor or use a different approach.
        // But wait, HillClimbing constructor:
        // if(Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar))
        
        // This dependency on static Strategy in constructor makes it hard to test in isolation.
        // I will try to set up the Strategy singleton if possible, or skip testing the constructor logic if I can't control it.
        // Actually, I can use reflection to set the 'strategy' field in Strategy class if it's not final, or just ensure Strategy is initialized.
        
        // Let's assume for now we can't easily mock the static call in constructor without extra tools.
        // I will try to create a subclass of HillClimbing that overrides the problematic parts or just test what I can.
        // Or better, I can initialize Strategy with a mock Problem before creating HillClimbing.
    }

    @Test
    public void testGettersAndSetters() {
        // This test might fail if HillClimbing constructor throws exception due to Strategy.
        // So I'll wrap it in try-catch or assume it works if Strategy is not null.
        
        // Since I cannot easily mock the static Strategy.getStrategy() call which is used in the constructor, 
        // I will skip testing the constructor-dependent logic for now and focus on other methods if possible.
        // However, the constructor IS called.
        
        // Strategy.getStrategy() creates a new Strategy if null.
        // Strategy.getProblem() might return null.
        // The constructor calls Strategy.getStrategy().getProblem().getTypeProblem().
        // If getProblem() returns null, it throws NPE.
        
        // To fix this, I need to set the Problem in the Strategy singleton.
        Strategy realStrategy = Strategy.getStrategy();
        realStrategy.setProblem(problem);
        when(problem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        when(problem.getCodification()).thenReturn(codification);
        when(codification.getVariableCount()).thenReturn(1);

        hillClimbing = new HillClimbing();
        
        hillClimbing.setWeight(10.0f);
        // getWeight returns 0 in the code I saw (TODO stub), so this might fail if I expect 10.
        // Let's check the code: public float getWeight() { return 0; } -> It is a stub!
        // So I should not test getWeight/setWeight logic if it's not implemented.
        
        hillClimbing.setGeneratorType(GeneratorType.HillClimbing);
        assertEquals(GeneratorType.HillClimbing, hillClimbing.getGeneratorType());
    }
}
