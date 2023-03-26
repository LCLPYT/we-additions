package work.lclpnet.weadditions.service;

import com.sk89q.worldedit.function.operation.Operation;

public interface OperationFilterService {

    boolean isBiomeModification(Operation operation);
}
