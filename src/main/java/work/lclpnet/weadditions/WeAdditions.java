package work.lclpnet.weadditions;

import work.lclpnet.weadditions.service.*;
import work.lclpnet.weadditions.service.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class WeAdditions {

    private static final Object mutex = new Object();
    private static WeAdditions instance;

    private final Map<Class<?>, Object> services = new HashMap<>();

    private WeAdditions() {
        // default service implementations
        registerService(ChunkSyncService.class, new ChunkSyncServiceImpl());
        registerService(OperationFilterService.class, new OperationFilterServiceImpl());
        registerService(ChunkChangeMemoryService.class, new ChunkChangeMemoryServiceImpl());
        registerService(ChunkChangePostProcessor.class, new ChunkSyncPostProcessor());
        registerService(ExtentWorldService.class, new ExtentWorldServiceImpl());
    }

    public static WeAdditions getInstance() {
        var ret = instance;
        if (ret == null) {
            synchronized (mutex) {
                ret = instance;
                if (instance == null) {
                    instance = ret = new WeAdditions();
                }
            }
        }

        return ret;
    }

    /**
     * Register or overwrite a service instance for a service class.
     *
     * @param serviceClass The service interface class.
     * @param service The service implementation instance.
     * @param <C> The service interface type.
     * @param <S> The service implementation type.
     */
    public <C, S extends C> void registerService(Class<C> serviceClass, S service) {
        Objects.requireNonNull(serviceClass);
        Objects.requireNonNull(service);

        Class<?> instanceClass = service.getClass();
        if (!serviceClass.isAssignableFrom(instanceClass)) {
            throw new IllegalArgumentException("%s does not extend %s".formatted(instanceClass, serviceClass));
        }

        services.put(serviceClass, service);
    }

    /**
     * Get a registered service implementation for a service interface.
     *
     * @param serviceClass The service interface class.
     * @return The service implementation.
     * @param <C> The service interface type.
     * @param <S> The service implementation type.
     */
    @SuppressWarnings("unchecked")
    public <C, S extends C> Optional<S> getService(Class<C> serviceClass) {
        return Optional.ofNullable(services.get(serviceClass))
                .map(service -> (S) service);
    }
}
