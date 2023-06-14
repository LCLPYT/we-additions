package work.lclpnet.weadditions.mixin;

import com.sk89q.worldedit.function.operation.ChangeSetExecutor;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;
import com.sk89q.worldedit.history.UndoContext;
import com.sk89q.worldedit.history.change.Change;
import com.sk89q.worldedit.history.changeset.ChangeSet;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import work.lclpnet.weadditions.WeAdditions;
import work.lclpnet.weadditions.service.ChunkChangeMemoryService;
import work.lclpnet.weadditions.service.ChunkChangePostProcessor;
import work.lclpnet.weadditions.service.ExtentWorldService;
import work.lclpnet.weadditions.type.ChunkMemory;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mixin(value = ChangeSetExecutor.class, remap = false)
public class ChangeSetExecutorMixin implements ChunkMemory {

    @Mutable
    @Unique
    @Final
    private Set<BlockVector2> affectedChunks;
    @Mutable
    @Unique
    @Final
    @Nullable
    private ChunkChangeMemoryService memoryService;
    @Unique
    @Nullable
    private World world = null;

    @Inject(
            method = "<init>",
            at = @At("TAIL"),
            remap = false
    )
    public void weAdditions$onConstruct(ChangeSet changeSet, ChangeSetExecutor.Type type, UndoContext context, CallbackInfo ci) {
        this.affectedChunks = new HashSet<>();

        WeAdditions weAdditions = WeAdditions.getInstance();
        this.memoryService = weAdditions.getService(ChunkChangeMemoryService.class).orElse(null);

        var extent = context.getExtent();
        if (extent != null) {
            this.world = weAdditions.getService(ExtentWorldService.class)
                    .map(service -> service.findWorld(extent))
                    .orElse(null);
        }
    }


    /**
     * This injection captures all affected chunks and saves them into the ChunkMemory.
     * The changes of which the chunks are captured are filtered by the {@link ChunkChangeMemoryService}.
     * @author LCLP
     */
    @Inject(
            method = "resume",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.GETFIELD,
                    target = "Lcom/sk89q/worldedit/function/operation/ChangeSetExecutor;type:Lcom/sk89q/worldedit/function/operation/ChangeSetExecutor$Type;"
            ),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void weAdditions$beforeExecute(RunContext run, CallbackInfoReturnable<Operation> cir, Change change) {
        if (memoryService != null) {
            memoryService.memorize(this, change);
        }
    }

    /**
     * This injection fires post-change actions on affected chunks.
     * @author LCLP
     */
    @Inject(
            method = "resume",
            at = @At("RETURN"),
            remap = false
    )
    public void weAdditions$afterFinished(RunContext run, CallbackInfoReturnable<Operation> cir) {
        WeAdditions.getInstance().getService(ChunkChangePostProcessor.class)
                .ifPresent(service -> service.postProcess(this));
    }

    @Override
    public Set<BlockVector2> weAdditions$getAffectedChunks() {
        return affectedChunks;
    }

    @Override
    public Optional<World> weAdditions$getWorld() {
        return Optional.ofNullable(world);
    }
}
