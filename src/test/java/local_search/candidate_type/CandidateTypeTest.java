package local_search.candidate_type;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import local_search.complement.StrategyType;
import metaheurictics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;

public class CandidateTypeTest {

    @Mock
    private Problem problem;
    
    @Mock
    private State stateReference;
    
    private CandidateValue candidateValue;
    private List<State> neighborhood;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Strategy.getStrategy().setProblem(problem);
        candidateValue = new CandidateValue();
        neighborhood = new ArrayList<>();
        neighborhood.add(stateReference);
    }

    @Test
    public void testRandomCandidate() throws Exception {
        State result = candidateValue.stateCandidate(stateReference, CandidateType.RandomCandidate, StrategyType.NORMAL, 1, neighborhood);
        assertNotNull(result);
    }
    
    @Test
    public void testGreaterCandidate() throws Exception {
        // Mock evaluation for comparison
        when(stateReference.getEvaluation()).thenReturn(new ArrayList<>(List.of(10.0)));
        State neighbor = mock(State.class);
        when(neighbor.getEvaluation()).thenReturn(new ArrayList<>(List.of(20.0)));
        neighborhood.add(neighbor);
        
        State result = candidateValue.stateCandidate(stateReference, CandidateType.GreaterCandidate, StrategyType.NORMAL, 1, neighborhood);
        assertNotNull(result);
    }
    
    @Test
    public void testSmallerCandidate() throws Exception {
        when(stateReference.getEvaluation()).thenReturn(new ArrayList<>(List.of(10.0)));
        State neighbor = mock(State.class);
        when(neighbor.getEvaluation()).thenReturn(new ArrayList<>(List.of(5.0)));
        neighborhood.add(neighbor);
        
        State result = candidateValue.stateCandidate(stateReference, CandidateType.SmallerCandidate, StrategyType.NORMAL, 1, neighborhood);
        assertNotNull(result);
    }
    
    @Test
    public void testNotDominatedCandidate() throws Exception {
        State result = candidateValue.stateCandidate(stateReference, CandidateType.NotDominatedCandidate, StrategyType.NORMAL, 1, neighborhood);
        assertNotNull(result);
    }
}
