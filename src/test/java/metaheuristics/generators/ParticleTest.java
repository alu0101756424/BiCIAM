package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

public class ParticleTest {

    @Mock
    private Problem problem;

    @Mock
    private State state;
    
    private Particle particle;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Strategy strategy = Strategy.getStrategy();
        strategy.setProblem(problem);
        
        particle = new Particle();
    }

    @Test
    public void testInitialization() {
        assertNotNull(particle.getStateActual());
        assertNotNull(particle.getStatePBest());
        assertNotNull(particle.getVelocity());
    }
    
    @Test
    public void testUpdateReference_Maximization_Improvement() throws Exception {
        // Setup Maximization
        when(problem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        // Setup states
        State actual = new State();
        ArrayList<Double> actualEval = new ArrayList<>();
        actualEval.add(10.0);
        actual.setEvaluation(actualEval);
        ArrayList<Object> actualCode = new ArrayList<>();
        actualCode.add(1.0);
        actual.setCode(actualCode);
        particle.setStateActual(actual);
        
        State pBest = new State();
        ArrayList<Double> pBestEval = new ArrayList<>();
        pBestEval.add(5.0);
        pBest.setEvaluation(pBestEval);
        particle.setStatePBest(pBest);
        
        // Execute
        particle.updateReference(null, 1);
        
        // Verify pBest updated to actual
        assertEquals(10.0, particle.getStatePBest().getEvaluation().get(0));
        assertEquals(1.0, particle.getStatePBest().getCode().get(0));
    }

    @Test
    public void testUpdateReference_Minimization_Improvement() throws Exception {
        // Setup Minimization
        when(problem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        
        // Setup states
        State candidate = new State();
        ArrayList<Double> candEval = new ArrayList<>();
        candEval.add(5.0);
        candidate.setEvaluation(candEval);
        ArrayList<Object> candCode = new ArrayList<>();
        candCode.add(2.0);
        candidate.setCode(candCode);
        
        State pBest = new State();
        ArrayList<Double> pBestEval = new ArrayList<>();
        pBestEval.add(10.0);
        pBest.setEvaluation(pBestEval);
        particle.setStatePBest(pBest);
        
        // Execute
        particle.updateReference(candidate, 1);
        
        // Verify pBest updated to candidate
        assertEquals(5.0, particle.getStatePBest().getEvaluation().get(0));
        assertEquals(2.0, particle.getStatePBest().getCode().get(0));
    }
    
    @Test
    public void testGenerate() throws Exception {
        // Setup static fields for PSO
        ParticleSwarmOptimization.wmax = 0.9;
        ParticleSwarmOptimization.wmin = 0.4;
        ParticleSwarmOptimization.countCurrentIterPSO = 1;
        ParticleSwarmOptimization.learning1 = 2;
        ParticleSwarmOptimization.learning2 = 2;
        ParticleSwarmOptimization.countParticle = 1;
        ParticleSwarmOptimization.countParticleBySwarm = 1;
        ParticleSwarmOptimization.binary = false;
        
        // Setup lBest array
        State lBestState = new State();
        ArrayList<Object> lBestCode = new ArrayList<>();
        lBestCode.add(1.0);
        lBestState.setCode(lBestCode);
        ParticleSwarmOptimization.lBest = new State[] { lBestState, lBestState }; // Ensure array is big enough
        
        // Setup Strategy
        Strategy.getStrategy().setCountMax(100);
        when(problem.getState()).thenReturn(state);
        ArrayList<Object> code = new ArrayList<>();
        code.add(0.0);
        when(state.getCode()).thenReturn(code);
        
        // Setup Particle
        particle.getVelocity().add(0.1);
        particle.getStateActual().getCode().add(0.5);
        particle.getStatePBest().getCode().add(0.8);
        
        // Execute
        State result = particle.generate(1);
        
        // Verify
        assertNull(result); // generate returns null
        assertNotNull(particle.getVelocity());
        assertEquals(1, particle.getVelocity().size());
        assertEquals(1, particle.getStateActual().getCode().size());
    }
}

