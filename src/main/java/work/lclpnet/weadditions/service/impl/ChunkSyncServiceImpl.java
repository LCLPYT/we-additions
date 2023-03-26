package work.lclpnet.weadditions.service.impl;

import com.sk89q.worldedit.fabric.FabricWorld;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.world.World;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.ChunkStatus;
import work.lclpnet.weadditions.service.ChunkSyncService;

import java.util.Collection;

public class ChunkSyncServiceImpl implements ChunkSyncService {

    @Override
    public void sync(World world, Collection<BlockVector2> chunks) {
        if (!(world instanceof FabricWorld fabricWorld)) return;

        var mcWorld = fabricWorld.getWorld();
        if (!(mcWorld instanceof ServerWorld serverWorld)) return;

        var mcChunks = chunks.stream()
                .map(chunkPos -> serverWorld.getChunk(chunkPos.getX(), chunkPos.getZ(), ChunkStatus.FULL, false))
                .distinct()
                .toList();

        serverWorld.getChunkManager().threadedAnvilChunkStorage.sendChunkBiomePackets(mcChunks);
    }
}
