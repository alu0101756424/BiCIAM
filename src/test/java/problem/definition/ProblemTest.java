package problem.definition;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import problem.extension.SolutionMethod;
import problem.extension.TypeSolutionMethod;
import factory_interface.IFFactorySolutionMethod;

public class ProblemTest {

    private Problem problem;

    @Mock
    private ObjetiveFunction objectiveFunction;

    @Mock
    private State state;

    @Mock
    private Codification codification;

    @Mock
    private Operator operator;

    @Mock
    private IFFactorySolutionMethod factorySolutionMethod;

    @Mock
    private SolutionMethod solutionMethod;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        problem = new Problem();
    }

    @Test
    public void testGettersAndSetters() {
        problem.setState(state);
        assertEquals(state, problem.getState());

        problem.setCodification(codification);
        assertEquals(codification, problem.getCodification());

        problem.setOperator(operator);
        assertEquals(operator, problem.getOperator());

        problem.setPossibleValue(10);
        assertEquals(10, problem.getPossibleValue());

        problem.setTypeProblem(Problem.ProblemType.Maximizar);
        assertEquals(Problem.ProblemType.Maximizar, problem.getTypeProblem());

        problem.setFactorySolutionMethod(factorySolutionMethod);
        assertEquals(factorySolutionMethod, problem.getFactorySolutionMethod());
    }

    @Test
    public void testEvaluateWithoutSolutionMethod() throws Exception {
        ArrayList<ObjetiveFunction> functions = new ArrayList<>();
        functions.add(objectiveFunction);
        problem.setFunction(functions);
        problem.setTypeSolutionMethod(null);

        when(objectiveFunction.Evaluation(state)).thenReturn(5.0);

        problem.Evaluate(state);

        verify(objectiveFunction).Evaluation(state);
        verify(state).setEvaluation(any(ArrayList.class));
    }
}
