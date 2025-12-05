package factory_method;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FactoryLoaderTest {

    @Test
    public void testGetInstance_ValidClass() throws Exception {
        Object instance = FactoryLoader.getInstance("java.lang.String");
        assertNotNull(instance);
        assertTrue(instance instanceof String);
    }

    @Test
    public void testGetInstance_InvalidClass() {
        assertThrows(ClassNotFoundException.class, () -> {
            FactoryLoader.getInstance("com.example.NonExistentClass");
        });
    }
}
