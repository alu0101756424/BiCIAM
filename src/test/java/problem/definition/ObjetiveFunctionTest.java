package problem.definition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import problem.definition.Problem.ProblemType;

public class ObjetiveFunctionTest {

    @Test
    public void testGettersAndSetters() {
        ObjetiveFunction function = new ObjetiveFunction() {
            @Override
            public Double Evaluation(State state) {
                return 0.0;
            }
        };

        function.setWeight(1.5f);
        assertEquals(1.5f, function.getWeight(), 0.001);

        function.setTypeProblem(ProblemType.Maximizar);
        assertEquals(ProblemType.Maximizar, function.getTypeProblem());
    }
}

