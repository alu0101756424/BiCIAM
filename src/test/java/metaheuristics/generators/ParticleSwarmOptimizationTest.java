package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import metaheurictics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

public class ParticleSwarmOptimizationTest {

    @Mock
    private Problem problem;

    @Mock
    private State state;
    
    @Mock
    private State statePBest;

    @Mock
    private Particle particle;

    private ParticleSwarmOptimization pso;

    @BeforeEach
    public void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
            Strategy strategy = Strategy.getStrategy();
            strategy.setProblem(problem);
            if (strategy.mapGenerators == null) {
                strategy.mapGenerators = new TreeMap<>();
            }
            // Populate mapGenerators to ensure getListKey() works and contains PSO
            ParticleSwarmOptimization mockPso = mock(ParticleSwarmOptimization.class);
            when(mockPso.getListParticle()).thenReturn(new ArrayList<>());
            strategy.mapGenerators.put(GeneratorType.ParticleSwarmOptimization, mockPso);
            
            // Reset static variables to ensure clean state
            ParticleSwarmOptimization.countParticle = 0;
            ParticleSwarmOptimization.coutSwarm = 1;
            ParticleSwarmOptimization.countParticleBySwarm = 1;
            ParticleSwarmOptimization.countRef = 1;
            ParticleSwarmOptimization.lBest = new State[1];
            ParticleSwarmOptimization.gBest = null;
            
            pso = new ParticleSwarmOptimization();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void testInitialization() {
        assertNotNull(pso);
        assertEquals(GeneratorType.ParticleSwarmOptimization, pso.getGeneratorType());
    }

    @Test
    public void testGenerate() throws Exception {
        List<Particle> particles = new ArrayList<>();
        particles.add(particle);
        pso.setListParticle(particles);
        
        when(particle.getStateActual()).thenReturn(state);
        
        State result = pso.generate(1);
        
        verify(particle).generate(1);
        assertEquals(state, result);
    }

    @Test
    public void testUpdateReferenceMaximization() throws Exception {
        when(problem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        List<Particle> particles = new ArrayList<>();
        particles.add(particle);
        pso.setListParticle(particles);
        
        // Setup particle behavior
        when(particle.getStatePBest()).thenReturn(statePBest);
        when(statePBest.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(100.0)));
        when(statePBest.getCode()).thenReturn(new ArrayList<>());
        
        // Setup lBest (local best)
        State currentLBest = mock(State.class);
        when(currentLBest.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(50.0)));
        ParticleSwarmOptimization.lBest[0] = currentLBest;
        
        // Setup reference list for gBest comparison
        State refState = mock(State.class);
        when(refState.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(80.0)));
        List<State> refList = new ArrayList<>();
        refList.add(refState);
        pso.setListStateReference(refList);

        pso.updateReference(state, 1);

        // Verify lBest updated
        assertEquals(statePBest, ParticleSwarmOptimization.lBest[0]);
        // Verify gBest updated (since 100 > 80)
        assertNotNull(ParticleSwarmOptimization.gBest);
    }

    @Test
    public void testUpdateReferenceMinimization() throws Exception {
        when(problem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        
        List<Particle> particles = new ArrayList<>();
        particles.add(particle);
        pso.setListParticle(particles);
        
        // Setup particle behavior
        when(particle.getStatePBest()).thenReturn(statePBest);
        when(statePBest.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(10.0))); // Lower is better
        when(statePBest.getCode()).thenReturn(new ArrayList<>());
        
        // Setup lBest (local best)
        State currentLBest = mock(State.class);
        when(currentLBest.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(50.0)));
        ParticleSwarmOptimization.lBest[0] = currentLBest;
        
        // Setup reference list for gBest comparison
        State refState = mock(State.class);
        when(refState.getEvaluation()).thenReturn(new ArrayList<>(Arrays.asList(20.0)));
        List<State> refList = new ArrayList<>();
        refList.add(refState);
        pso.setListStateReference(refList);

        // Mock particle updateReference call
        doNothing().when(particle).updateReference(any(State.class), anyInt());

        pso.updateReference(state, 1);

        // Verify particle update called
        verify(particle).updateReference(state, 1);
        
        // Verify lBest updated (10 < 50)
        assertEquals(statePBest, ParticleSwarmOptimization.lBest[0]);
        // Verify gBest updated (10 < 20)
        assertNotNull(ParticleSwarmOptimization.gBest);
    }
}
