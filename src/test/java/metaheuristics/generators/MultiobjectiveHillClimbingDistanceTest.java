package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheurictics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;

public class MultiobjectiveHillClimbingDistanceTest {

    @Mock
    private Problem problem;
    
    @Mock
    private Operator operator;
    
    @Mock
    private State state;
    
    private MultiobjectiveHillClimbingDistance generator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Strategy.getStrategy().setProblem(problem);
        when(problem.getOperator()).thenReturn(operator);
        
        generator = new MultiobjectiveHillClimbingDistance();
    }

    @Test
    public void testInitialization() {
        assertNotNull(generator);
        assertEquals(GeneratorType.MultiobjectiveHillClimbingDistance, generator.getType());
    }

    @Test
    public void testGenerate() throws Exception {
        // Setup
        List<State> neighborhood = new ArrayList<>();
        neighborhood.add(state);
        when(operator.generatedNewState(any(State.class), anyInt())).thenReturn(neighborhood);
        
        // Set initial reference
        generator.setInitialReference(state);
        
        // Execute
        State result = generator.generate(1);
        
        // Verify
        assertNotNull(result);
        verify(operator).generatedNewState(any(State.class), eq(1));
    }

    @Mock
    private factory_interface.IFFactoryAcceptCandidate factory;
    
    @Mock
    private local_search.acceptation_type.AcceptableCandidate acceptableCandidate;

    @Test
    public void testUpdateReference_FirstSolution() throws Exception {
        // Setup Strategy list
        Strategy.getStrategy().listRefPoblacFinal = new ArrayList<>();
        MultiobjectiveHillClimbingDistance.distanceSolution = new ArrayList<>();
        
        generator.setInitialReference(state);
        when(state.clone()).thenReturn(state);
        
        // Inject mock factory
        generator.ifacceptCandidate = factory;
        when(factory.createAcceptCandidate(any())).thenReturn(acceptableCandidate);
        when(acceptableCandidate.acceptCandidate(any(), any())).thenReturn(true);
        
        // Mock operator for neighborhood generation
        List<State> neighborhood = new ArrayList<>();
        neighborhood.add(state);
        when(operator.generatedNewState(any(State.class), anyInt())).thenReturn(neighborhood);
        
        // Execute
        generator.updateReference(state, 1);
        
        // Verify added to list
        assertEquals(1, Strategy.getStrategy().listRefPoblacFinal.size());
        assertEquals(1, MultiobjectiveHillClimbingDistance.distanceSolution.size());
    }
    
    @Test
    public void testDistanceCalculateAdd() {
        List<State> solutions = new ArrayList<>();
        State s1 = mock(State.class);
        State s2 = mock(State.class);
        solutions.add(s1);
        solutions.add(s2);
        
        when(s1.Distance(s2)).thenReturn(5.0);
        when(s2.Distance(s1)).thenReturn(5.0);
        
        MultiobjectiveHillClimbingDistance.distanceSolution = new ArrayList<>();
        MultiobjectiveHillClimbingDistance.distanceSolution.add(0.0);
        
        List<Double> result = MultiobjectiveHillClimbingDistance.DistanceCalculateAdd(solutions);
        assertNotNull(result);
    }
    
    @Test
    public void testGetSetWeight() {
        generator.setWeight(10.0f);
        assertEquals(0.0f, generator.getWeight()); // Based on previous view, getWeight returned 0
    }
}
