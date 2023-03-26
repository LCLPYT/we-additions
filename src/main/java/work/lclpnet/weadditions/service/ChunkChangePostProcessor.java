package work.lclpnet.weadditions.service;

import work.lclpnet.weadditions.type.ChunkMemory;

/**
 * Handles actions after a changes have been made, e.g. through <code>//undo</code> or <code>//redo</code>.
 */
public interface ChunkChangePostProcessor {

    void postProcess(ChunkMemory memory);
}
