package problem.definition;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

public class OperatorTest {

    private class OperatorStub extends Operator {
        @Override
        public List<State> generatedNewState(State stateCurrent, Integer operatornumber) {
            return new ArrayList<>();
        }
        @Override
        public List<State> generateRandomState(Integer operatornumber) {
            return new ArrayList<>();
        }
    }

    @Test
    public void testAbstractImplementation() {
        OperatorStub stub = new OperatorStub();
        assertNotNull(stub);
        assertNotNull(stub.generatedNewState(new State(), 1));
        assertNotNull(stub.generateRandomState(1));
    }
}

