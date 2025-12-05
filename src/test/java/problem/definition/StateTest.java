package problem.definition;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    @Test
    public void testStateInitialization() {
        State state = new State();
        assertNull(state.getCode());
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
}
