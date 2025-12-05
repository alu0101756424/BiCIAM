package problem.definition;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CodificationTest {

    private class CodificationStub extends Codification {
        @Override
        public boolean validState(State state) { return true; }
        @Override
        public Object getVariableAleatoryValue(int key) { return null; }
        @Override
        public int getAleatoryKey() { return 0; }
        @Override
        public int getVariableCount() { return 0; }
    }

    @Test
    public void testAbstractImplementation() {
        CodificationStub stub = new CodificationStub();
        assertNotNull(stub);
        assertTrue(stub.validState(new State()));
        assertEquals(0, stub.getAleatoryKey());
    }
}

