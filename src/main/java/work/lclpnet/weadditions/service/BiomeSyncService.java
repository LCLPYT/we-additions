package work.lclpnet.weadditions.service;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;

public interface BiomeSyncService {

    void sync(World world, Region region);
}
