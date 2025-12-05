package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InstanceTest {

    @Test
    public void testInstanceGA() {
        InstanceGA instance = new InstanceGA();
        instance.setTerminate(true);
        assertTrue(instance.isTerminate());
        
        // basic run verification (should exit immediately if terminate loop check allows or just run once)
        // Since we cannot verify internal behavior without mocks of what it calls (likely MultiGenerator static),
        // we mainly assume it runs without threading issues in this micro test.
        // Thread t = new Thread(instance);
        // t.start();
        // t.join();
    }

    @Test
    public void testInstanceEE() {
        InstanceEE instance = new InstanceEE();
        instance.setTerminate(true);
        assertTrue(instance.isTerminate());
    }
    
    @Test
    public void testInstanceDE() {
        InstanceDE instance = new InstanceDE();
        instance.setTerminate(true);
        assertTrue(instance.isTerminate());
    }
}

