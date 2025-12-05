package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import factory_method.FactoryAcceptCandidate;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.acceptation_type.AcceptType;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;

public class MultiobjectiveHillClimbingRestartTest {

    private MultiobjectiveHillClimbingRestart generator;
    
    @Mock
    private Strategy strategyMock;
    
    @Mock
    private Problem problemMock;
    
    @Mock
    private Operator operatorMock;
    
    @Mock
    private State stateMock;
    
    @Mock
    private CandidateValue candidateValueMock;
    
    private MockedStatic<Strategy> strategyStaticMock;
    
    private List<State> listRefPoblacFinal;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        // Reset static fields
        MultiobjectiveHillClimbingRestart.sizeNeighbors = 5;
        
        // Mock Strategy Singleton
        strategyStaticMock = mockStatic(Strategy.class);
        strategyStaticMock.when(Strategy::getStrategy).thenReturn(strategyMock);
        
        // Setup Strategy mock
        when(strategyMock.getProblem()).thenReturn(problemMock);
        when(problemMock.getOperator()).thenReturn(operatorMock);
        
        // Setup listRefPoblacFinal
        listRefPoblacFinal = new ArrayList<>();
        Field listRefField = Strategy.class.getDeclaredField("listRefPoblacFinal");
        listRefField.setAccessible(true);
        listRefField.set(strategyMock, listRefPoblacFinal);
        
        generator = new MultiobjectiveHillClimbingRestart();
        
        // Inject mocks into generator
        Field candidateValueField = MultiobjectiveHillClimbingRestart.class.getDeclaredField("candidatevalue");
        candidateValueField.setAccessible(true);
        candidateValueField.set(generator, candidateValueMock);
        
        // Set initial state
        generator.stateReferenceHC = stateMock;
    }

    @AfterEach
    void tearDown() {
        strategyStaticMock.close();
    }

    @Test
    void testConstructor() {
        assertNotNull(generator);
        assertEquals(GeneratorType.MultiobjectiveHillClimbingRestart, generator.getType());
        assertEquals(50.0f, generator.getWeight());
    }

    @Test
    void testGenerate() throws Exception {
        List<State> neighborhood = new ArrayList<>();
        neighborhood.add(stateMock);
        
        when(operatorMock.generatedNewState(any(State.class), anyInt())).thenReturn(neighborhood);
        when(candidateValueMock.stateCandidate(any(State.class), any(CandidateType.class), any(StrategyType.class), anyInt(), anyList()))
            .thenReturn(stateMock);
            
        State result = generator.generate(1);
        
        assertNotNull(result);
        verify(operatorMock).generatedNewState(any(State.class), anyInt());
        verify(candidateValueMock).stateCandidate(any(State.class), any(CandidateType.class), any(StrategyType.class), anyInt(), anyList());
    }

    @Test
    void testUpdateReference_FirstSolution() throws Exception {
        State candidateState = mock(State.class);
        when(candidateState.clone()).thenReturn(candidateState);
        when(stateMock.clone()).thenReturn(stateMock);
        
        // Mock FactoryAcceptCandidate
        try (MockedConstruction<FactoryAcceptCandidate> factoryMock = mockConstruction(FactoryAcceptCandidate.class,
                (mock, context) -> {
                    AcceptableCandidate acceptableCandidateMock = mock(AcceptableCandidate.class);
                    when(mock.createAcceptCandidate(any(AcceptType.class))).thenReturn(acceptableCandidateMock);
                    when(acceptableCandidateMock.acceptCandidate(any(State.class), any(State.class))).thenReturn(true);
                })) {
            
            generator.updateReference(candidateState, 1);
            
            // Verify first solution added
            assertEquals(1, listRefPoblacFinal.size());
        }
    }
    
    @Test
    void testUpdateReference_AcceptCandidate() throws Exception {
        State candidateState = mock(State.class);
        when(candidateState.clone()).thenReturn(candidateState);
        when(stateMock.clone()).thenReturn(stateMock);
        
        // Pre-populate listRefPoblacFinal
        listRefPoblacFinal.add(stateMock);
        
        // Mock FactoryAcceptCandidate
        try (MockedConstruction<FactoryAcceptCandidate> factoryMock = mockConstruction(FactoryAcceptCandidate.class,
                (mock, context) -> {
                    AcceptableCandidate acceptableCandidateMock = mock(AcceptableCandidate.class);
                    when(mock.createAcceptCandidate(any(AcceptType.class))).thenReturn(acceptableCandidateMock);
                    when(acceptableCandidateMock.acceptCandidate(any(State.class), any(State.class))).thenReturn(true);
                })) {
            
            generator.updateReference(candidateState, 1);
            
            // Verify stateReferenceHC updated
            verify(candidateState, atLeastOnce()).clone();
        }
    }
    @Test
    void testUpdateReference_RejectFirst_ThenAcceptFromNeighborhood() throws Exception {
        State candidateState = mock(State.class);
        when(candidateState.clone()).thenReturn(candidateState);
        when(stateMock.clone()).thenReturn(stateMock);
        State neighborState = mock(State.class);
        when(neighborState.clone()).thenReturn(neighborState);
        
        // Pre-populate listRefPoblacFinal
        listRefPoblacFinal.add(stateMock);
        
        // Mock neighborhood with one valid candidate
        List<State> neighborhood = new ArrayList<>();
        neighborhood.add(neighborState);
        when(operatorMock.generatedNewState(any(State.class), anyInt())).thenReturn(neighborhood);
        
        // Mock FactoryAcceptCandidate
        try (MockedConstruction<FactoryAcceptCandidate> factoryMock = mockConstruction(FactoryAcceptCandidate.class,
                (mock, context) -> {
                    AcceptableCandidate acceptableCandidateMock = mock(AcceptableCandidate.class);
                    when(mock.createAcceptCandidate(any(AcceptType.class))).thenReturn(acceptableCandidateMock);
                    // First call returns false (reject initial), second call returns true (accept neighbor)
                    when(acceptableCandidateMock.acceptCandidate(any(State.class), any(State.class)))
                        .thenReturn(false)
                        .thenReturn(true);
                })) {
            
            generator.updateReference(candidateState, 1);
            
            // Verify neighbor was evaluated
            verify(problemMock).Evaluate(neighborState);
            // Verify stateReferenceHC updated to neighbor
            assertEquals(neighborState, generator.getReference());
        }
    }

    @Test
    void testUpdateReference_RejectNeighborhood_ThenAcceptRandom() throws Exception {
        State candidateState = mock(State.class);
        when(candidateState.clone()).thenReturn(candidateState);
        when(stateMock.clone()).thenReturn(stateMock);
        State randomState = mock(State.class);
        when(randomState.clone()).thenReturn(randomState);
        
        // Pre-populate listRefPoblacFinal
        listRefPoblacFinal.add(stateMock);
        
        // Mock empty neighborhood to force random generation
        when(operatorMock.generatedNewState(any(State.class), anyInt())).thenReturn(new ArrayList<>());
        
        // Mock random state generation
        List<State> randomList = new ArrayList<>();
        randomList.add(randomState);
        when(operatorMock.generateRandomState(anyInt())).thenReturn(randomList);
        
        // Mock FactoryAcceptCandidate
        try (MockedConstruction<FactoryAcceptCandidate> factoryMock = mockConstruction(FactoryAcceptCandidate.class,
                (mock, context) -> {
                    AcceptableCandidate acceptableCandidateMock = mock(AcceptableCandidate.class);
                    when(mock.createAcceptCandidate(any(AcceptType.class))).thenReturn(acceptableCandidateMock);
                    // First call returns false (reject initial), second call returns true (accept random)
                    when(acceptableCandidateMock.acceptCandidate(any(State.class), any(State.class)))
                        .thenReturn(false)
                        .thenReturn(true);
                })) {
            
            generator.updateReference(candidateState, 1);
            
            // Verify random state was evaluated
            verify(problemMock).Evaluate(randomState);
            // Verify stateReferenceHC updated to random state
            assertEquals(randomState, generator.getReference());
        }
    }
}
