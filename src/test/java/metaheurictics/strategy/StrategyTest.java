package metaheurictics.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    @Test
    public void testSingleton() {
        Strategy s1 = Strategy.getStrategy();
        Strategy s2 = Strategy.getStrategy();
        
        assertNotNull(s1);
        assertSame(s1, s2);
    }
}
