package work.lclpnet.weadditions.service;

import com.sk89q.worldedit.function.operation.Operation;

/**
 * This service provides methods to filter operations.
 */
public interface OperationFilterService {

    boolean isBiomeModification(Operation operation);
}
