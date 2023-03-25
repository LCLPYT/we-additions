package work.lclpnet.weadditions.mixin;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.command.BiomeCommands;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.fabric.FabricWorld;
import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.visitor.RegionVisitor;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.biome.BiomeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = BiomeCommands.class, remap = false)
public class BiomeCommandsMixin {

    /**
     * This injection sends chunk update packets after the execution of the setBiome command.
     * @author LCLP
     */
    @Inject(
            method = "setBiome",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/sk89q/worldedit/function/operation/Operations;completeLegacy(Lcom/sk89q/worldedit/function/operation/Operation;)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            remap = false
    )
    public void weAdditions$onBiomesSet(Actor actor, World world, LocalSession session, EditSession editSession,
                                        BiomeType target, boolean atPosition, CallbackInfo ci,
                                        Region region, Mask mask, RegionFunction replace, RegionVisitor visitor) {

        if (!(world instanceof FabricWorld fabricWorld)) return;

        var mcWorld = fabricWorld.getWorld();
        if (!(mcWorld instanceof ServerWorld serverWorld)) return;

        var chunks = region.getChunks().stream()
                .map(chunkPos -> serverWorld.getChunk(chunkPos.getX(), chunkPos.getZ(), ChunkStatus.FULL, false))
                .toList();

        serverWorld.getChunkManager().threadedAnvilChunkStorage.sendChunkBiomePackets(chunks);
    }
}
