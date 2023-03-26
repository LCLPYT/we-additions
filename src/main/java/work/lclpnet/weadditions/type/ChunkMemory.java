package work.lclpnet.weadditions.type;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.world.World;

import java.util.Optional;
import java.util.Set;

public interface ChunkMemory {

    Set<BlockVector2> weAdditions$getAffectedChunks();

    Optional<World> weAdditions$getWorld();
}
