package work.lclpnet.weadditions.service.impl;

import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.function.biome.BiomeReplace;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.visitor.RegionVisitor;
import work.lclpnet.weadditions.service.OperationFilterService;

import java.lang.reflect.Field;

public class OperationFilterServiceImpl implements OperationFilterService {

    @Override
    public boolean isBiomeModification(Operation operation) {
        if (!(operation instanceof RegionVisitor visitor)) return false;

        RegionFunction function;
        try {
            Field field = RegionVisitor.class.getDeclaredField("function");
            field.setAccessible(true);

            function = (RegionFunction) field.get(visitor);
        } catch (ReflectiveOperationException ignored) {
            return false;
        }

        return function instanceof BiomeReplace;
    }
}
