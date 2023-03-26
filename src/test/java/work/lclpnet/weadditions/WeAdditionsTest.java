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

        assertNull(instance.getService(TestInterface.class));

        final var serviceInstance = new TestImpl();
        instance.registerService(TestInterface.class, serviceInstance);

        var service = instance.getService(TestInterface.class);
        assertEquals(serviceInstance, service);
    }

    private interface TestInterface {
        void test();
    }

    private static class TestImpl implements TestInterface {
        @Override public void test() {}
    }
}