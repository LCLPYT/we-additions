package work.lclpnet.weadditions.service;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.world.World;

import java.util.Collection;

/**
 * Sends chunk update packets to every tracking player.
 * This causes biome updates on the client, so that the players do not have to rejoin.
 */
public interface ChunkSyncService {

    void sync(World world, Collection<BlockVector2> chunks);
}
