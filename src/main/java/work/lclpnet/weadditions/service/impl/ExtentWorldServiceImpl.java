package work.lclpnet.weadditions.service.impl;

import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.validation.DataValidatorExtent;
import com.sk89q.worldedit.world.World;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.weadditions.service.ExtentWorldService;

import java.lang.reflect.Field;

public class ExtentWorldServiceImpl implements ExtentWorldService {

    @Nullable
    @Override
    public World findWorld(Extent extent) {
        if (extent instanceof World world) {
            return world;
        }

        if (extent instanceof DataValidatorExtent dataValidatorExtent) {
            return getDataValidatorExtendWorld(dataValidatorExtent);
        }

        return null;
    }

    private World getDataValidatorExtendWorld(DataValidatorExtent extent) {
        try {
            Field field = DataValidatorExtent.class.getDeclaredField("world");
            field.setAccessible(true);
            return (World) field.get(extent);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
