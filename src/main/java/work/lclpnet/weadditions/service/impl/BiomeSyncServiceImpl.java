package work.lclpnet.weadditions.service.impl;

import com.sk89q.worldedit.fabric.FabricWorld;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.ChunkStatus;
import work.lclpnet.weadditions.service.BiomeSyncService;

public class BiomeSyncServiceImpl implements BiomeSyncService {

    @Override
    public void sync(World world, Region region) {
        if (!(world instanceof FabricWorld fabricWorld)) return;

        var mcWorld = fabricWorld.getWorld();
        if (!(mcWorld instanceof ServerWorld serverWorld)) return;

        var chunks = region.getChunks().stream()
                .map(chunkPos -> serverWorld.getChunk(chunkPos.getX(), chunkPos.getZ(), ChunkStatus.FULL, false))
                .toList();

        serverWorld.getChunkManager().threadedAnvilChunkStorage.sendChunkBiomePackets(chunks);
    }
}
