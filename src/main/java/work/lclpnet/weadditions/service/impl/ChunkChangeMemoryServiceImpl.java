package work.lclpnet.weadditions.service.impl;

import com.sk89q.worldedit.history.change.BiomeChange3D;
import com.sk89q.worldedit.history.change.Change;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.world.storage.ChunkStore;
import work.lclpnet.weadditions.service.ChunkChangeMemoryService;
import work.lclpnet.weadditions.type.ChunkMemory;

public class ChunkChangeMemoryServiceImpl implements ChunkChangeMemoryService {

    @Override
    public void memorize(ChunkMemory memory, Change change) {
        if (!(change instanceof BiomeChange3D biomeChange)) return;

        var pos = biomeChange.getPosition();
        int chunkX = pos.getX() >> ChunkStore.CHUNK_SHIFTS;
        int chunkZ = pos.getZ() >> ChunkStore.CHUNK_SHIFTS;

        var chunkPos = BlockVector2.at(chunkX, chunkZ);

        memory.weAdditions$getAffectedChunks().add(chunkPos);
    }
}
