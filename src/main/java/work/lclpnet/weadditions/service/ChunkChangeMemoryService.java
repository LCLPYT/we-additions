package work.lclpnet.weadditions.service;

import com.sk89q.worldedit.history.change.Change;
import work.lclpnet.weadditions.type.ChunkMemory;

/**
 * Responsible for memorizing affected chunks of {@link Change}s.
 * Implementations can decide whether affected chunks should be saved for the {@link Change} type.
 * For example, an implementation could only memorize affected chunks of {@link com.sk89q.worldedit.history.change.BiomeChange3D}s.
 */
public interface ChunkChangeMemoryService {

    void memorize(ChunkMemory memory, Change change);
}
