package metaheuristics.strategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import problem.definition.Problem;

public class StrategyTest {

    @Mock
    private Problem problem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Reset singleton before each test to ensure clean state
        Strategy.reset();
    }

    @Test
    public void testSingleton() {
        Strategy s1 = Strategy.getStrategy();
        Strategy s2 = Strategy.getStrategy();
        
        assertNotNull(s1);
        assertSame(s1, s2);
    }

    @Test
    public void testGetSetProblem() {
        Strategy strategy = Strategy.getStrategy();
        strategy.setProblem(problem);
        assertSame(problem, strategy.getProblem());
    }

    @Test
    public void testGetSetCountMax() {
        Strategy strategy = Strategy.getStrategy();
        strategy.setCountMax(100);
        assertEquals(100, strategy.getCountMax());
    }

    @Test
    public void testGetSetCountCurrent() {
        Strategy strategy = Strategy.getStrategy();
        strategy.setCountCurrent(50);
        assertEquals(50, strategy.getCountCurrent());
    }
    
    @Test
    public void testGetSetListKey() {
        Strategy strategy = Strategy.getStrategy();
        List<String> keys = new ArrayList<>();
        keys.add("Key1");
        strategy.setListKey(keys);
        assertSame(keys, strategy.getListKey());
        assertEquals(1, strategy.getListKey().size());
    }
    
    @Test
    public void testListsInitialization() {
        Strategy strategy = Strategy.getStrategy();
        assertNotNull(strategy.listRefPoblacFinal);
        assertNotNull(strategy.listBest);
        assertNotNull(strategy.mapGenerators);
    }
    
    @Test
    public void testGetBestState() {
        Strategy strategy = Strategy.getStrategy();
        assertNull(strategy.getBestState());
        
        problem.definition.State state = mock(problem.definition.State.class);
        strategy.listBest.add(state);
        assertSame(state, strategy.getBestState());
    }
    
    @Test
    public void testPublicFields() {
        Strategy strategy = Strategy.getStrategy();
        assertNull(strategy.generator);
        assertNull(strategy.listStates);
        
        metaheuristics.generators.Generator generator = mock(metaheuristics.generators.Generator.class);
        strategy.generator = generator;
        assertSame(generator, strategy.generator);
        
        List<problem.definition.State> states = new ArrayList<>();
        strategy.listStates = states;
        assertSame(states, strategy.listStates);
    }
}

