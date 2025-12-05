package problem.definition;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    @Test
    public void testStateInitialization() {
        State state = new State();
        assertNotNull(state.getCode());
        assertTrue(state.getCode().isEmpty());
        assertEquals(0, state.getNumber());
    }

    @Test
    public void testSettersAndGetters() {
        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        code.add(1);
        code.add(2);
        
        state.setCode(code);
        state.setNumber(5);
        
        assertEquals(code, state.getCode());
        assertEquals(5, state.getNumber());
    }

    @Test
    public void testDistance() {
        State s1 = new State();
        s1.setCode(new ArrayList<>(java.util.List.of(1, 2, 3)));
        
        State s2 = new State();
        s2.setCode(new ArrayList<>(java.util.List.of(1, 5, 3)));
        
        // Distance should be 1 (index 1 differs)
        assertEquals(1.0, s1.Distance(s2), 0.001);
        
        State s3 = new State();
        s3.setCode(new ArrayList<>(java.util.List.of(4, 5, 6)));
        // All 3 differ
        assertEquals(3.0, s1.Distance(s3), 0.001);
    }

    @Test
    public void testComparator() {
        State s1 = new State();
        s1.setCode(new ArrayList<>(java.util.List.of("A", "B")));
        
        State s2 = new State();
        s2.setCode(new ArrayList<>(java.util.List.of("A", "B")));
        
        State s3 = new State();
        s3.setCode(new ArrayList<>(java.util.List.of("A", "C")));
        
        assertTrue(s1.Comparator(s2));
        assertFalse(s1.Comparator(s3));
    }

    @Test
    public void testCloneAndCopy() {
        State original = new State();
        original.setCode(new ArrayList<>(java.util.List.of(10)));
        original.setNumber(1);
        original.setTypeGenerator(metaheuristics.generators.GeneratorType.GeneticAlgorithm);
        
        State clone = original.clone();
        assertEquals(original, clone); // Implementation returns 'this'
        
        Object copyObj = original.getCopy();
        assertTrue(copyObj instanceof State);
        State copy = (State) copyObj;
        assertNotSame(original, copy);
        assertEquals(original.getCode(), copy.getCode());
    }
}

