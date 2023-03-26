package work.lclpnet.weadditions.service;

import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.world.World;

import javax.annotation.Nullable;

/**
 * A service that finds a world from an {@link Extent}.
 */
public interface ExtentWorldService {

    @Nullable
    World findWorld(Extent extent);
}
