package work.lclpnet.weadditions.service.impl;

import work.lclpnet.weadditions.WeAdditions;
import work.lclpnet.weadditions.service.ChunkChangePostProcessor;
import work.lclpnet.weadditions.service.ChunkSyncService;
import work.lclpnet.weadditions.type.ChunkMemory;

public class ChunkSyncPostProcessor implements ChunkChangePostProcessor {

    @Override
    public void postProcess(ChunkMemory memory) {
        var chunks = memory.weAdditions$getAffectedChunks();
        if (chunks.isEmpty()) return;

        var world = memory.weAdditions$getWorld().orElse(null);
        if (world == null) return;

        WeAdditions.getInstance().getService(ChunkSyncService.class)
                .ifPresent(service -> service.sync(world, chunks));
    }
}
