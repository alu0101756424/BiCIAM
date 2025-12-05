package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheurictics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;
import problem.definition.ObjetiveFunction;

public class AcceptationTypeTest {

    @Mock
    private Problem problem;

    @Mock
    private State stateCurrent;

    @Mock
    private State stateCandidate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Strategy strategy = Strategy.getStrategy();
        strategy.setProblem(problem);
    }

    @Test
    public void testAcceptAnyone() {
        AcceptAnyone acceptAnyone = new AcceptAnyone();
        assertTrue(acceptAnyone.acceptCandidate(stateCurrent, stateCandidate));
    }

    @Test
    public void testAcceptBestMaximization() throws Exception {
        AcceptBest acceptBest = new AcceptBest();
        when(problem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        when(stateCurrent.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(10.0)));
        when(stateCandidate.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(20.0)));
        
        assertTrue(acceptBest.acceptCandidate(stateCurrent, stateCandidate));
        
        when(stateCandidate.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(5.0)));
        assertFalse(acceptBest.acceptCandidate(stateCurrent, stateCandidate));
    }

    @Test
    public void testAcceptBestMinimization() throws Exception {
        AcceptBest acceptBest = new AcceptBest();
        when(problem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        
        when(stateCurrent.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(10.0)));
        when(stateCandidate.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(5.0)));
        
        assertTrue(acceptBest.acceptCandidate(stateCurrent, stateCandidate));
        
        when(stateCandidate.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(20.0)));
        assertFalse(acceptBest.acceptCandidate(stateCurrent, stateCandidate));
    }

    @Test
    public void testAcceptNotBadMaximization() throws Exception {
        AcceptNotBad acceptNotBad = new AcceptNotBad();
        when(problem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        // Mock function list size to enter the loop
        ArrayList<ObjetiveFunction> functions = new ArrayList<>();
        functions.add(mock(ObjetiveFunction.class));
        when(problem.getFunction()).thenReturn(functions);

        when(stateCurrent.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(10.0)));
        when(stateCandidate.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(20.0)));
        
        assertTrue(acceptNotBad.acceptCandidate(stateCurrent, stateCandidate));
        
        when(stateCandidate.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(5.0)));
        assertFalse(acceptNotBad.acceptCandidate(stateCurrent, stateCandidate));
    }
}
