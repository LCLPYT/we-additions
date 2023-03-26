package work.lclpnet.weadditions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeAdditionsTest {

    @Test
    void getInstance() {
        var instance = WeAdditions.getInstance();
        var anotherInstance = WeAdditions.getInstance();

        assertNotNull(instance);
        assertEquals(instance, anotherInstance);
    }

    @Test
    void getService() {
        var instance = WeAdditions.getInstance();

        assertTrue(instance.getService(TestInterface.class).isEmpty());

        final var serviceInstance = new TestImpl();
        instance.registerService(TestInterface.class, serviceInstance);

        var service = instance.getService(TestInterface.class).orElseThrow();
        assertEquals(serviceInstance, service);
    }

    private interface TestInterface {}

    private static class TestImpl implements TestInterface {}
}