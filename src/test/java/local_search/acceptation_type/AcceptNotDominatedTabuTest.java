package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*; // For assertions
import static org.mockito.Mockito.*; // For mocking

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;
import local_search.complement.TabuSolutions;

class AcceptNotDominatedTabuTest {

    @Mock
    private Strategy strategyMock;
    
    @Mock
    private Problem problemMock;
    
    private MockedStatic<Strategy> strategyStaticMock;
    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        
        // Setup singleton mock
        strategyStaticMock = mockStatic(Strategy.class);
        strategyStaticMock.when(Strategy::getStrategy).thenReturn(strategyMock);
        
        when(strategyMock.getProblem()).thenReturn(problemMock);
        
        // Reset Tabu list
        TabuSolutions.listTabu = new ArrayList<>();
        TabuSolutions.maxelements = 10;
    }

    @AfterEach
    void tearDown() throws Exception {
        strategyStaticMock.close();
        mocks.close();
        if (TabuSolutions.listTabu != null) {
            TabuSolutions.listTabu.clear();
        }
    }

    @Test
    void testAcceptCandidate_Tabu_Rejected() {
        State current = mock(State.class);
        State candidate = mock(State.class);
        State tabuState = mock(State.class);
        
        TabuSolutions.listTabu.add(tabuState);
        
        // Candidate is equal to tabu state
        when(candidate.Comparator(tabuState)).thenReturn(true);
        
        AcceptNotDominatedTabu accept = new AcceptNotDominatedTabu();
        
        assertFalse(accept.acceptCandidate(current, candidate), "Should reject if candidate is in tabu list");
    }

    @Test
    void testAcceptCandidate_NotTabu_NotDominated_Accepted() {
        // Maximization problem
        when(problemMock.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        State current = mock(State.class);
        State candidate = mock(State.class);
        
        // Current: [10.0], Candidate: [20.0]
        // Current (10) < Candidate (20). Current does NOT dominate candidate.
        when(current.getEvaluation()).thenReturn(new ArrayList<>(List.of(10.0)));
        when(candidate.getEvaluation()).thenReturn(new ArrayList<>(List.of(20.0)));
        
        AcceptNotDominatedTabu accept = new AcceptNotDominatedTabu();
        
        assertTrue(accept.acceptCandidate(current, candidate), "Should accept if not in tabu and not dominated (candidate is better)");
    }
    
    @Test
    void testAcceptCandidate_NotTabu_Dominated_Rejected() {
        // Maximization problem
        when(problemMock.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        State current = mock(State.class);
        State candidate = mock(State.class);
        
        // Current: [20.0], Candidate: [10.0]
        // Current (20) > Candidate (10). Current DOMINATES candidate.
        when(current.getEvaluation()).thenReturn(new ArrayList<>(List.of(20.0)));
        when(candidate.getEvaluation()).thenReturn(new ArrayList<>(List.of(10.0)));
        
        AcceptNotDominatedTabu accept = new AcceptNotDominatedTabu();
        
        assertFalse(accept.acceptCandidate(current, candidate), "Should reject if dominated by current");
    }
}
