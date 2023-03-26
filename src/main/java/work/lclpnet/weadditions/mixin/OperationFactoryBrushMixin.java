package work.lclpnet.weadditions.mixin;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.command.tool.brush.OperationFactoryBrush;
import com.sk89q.worldedit.function.EditContext;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import work.lclpnet.weadditions.WeAdditions;
import work.lclpnet.weadditions.service.ChunkSyncService;
import work.lclpnet.weadditions.service.OperationFilterService;

@Mixin(value = OperationFactoryBrush.class, remap = false)
public class OperationFactoryBrushMixin {

    /**
     * This injection sends chunk update packets after the brush has been used for biome modification.
     * @author LCLP
     */
    @Inject(
            method = "build",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/sk89q/worldedit/function/operation/Operations;completeLegacy(Lcom/sk89q/worldedit/function/operation/Operation;)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            remap = false
    )
    public void weAdditions$afterBiomeBrush(EditSession editSession, BlockVector3 position, Pattern pattern, double size,
                                            CallbackInfo ci, EditContext context, Operation operation) {

        var weAdditions = WeAdditions.getInstance();

        var opFilter = weAdditions.getService(OperationFilterService.class).orElse(null);
        if (opFilter == null || !opFilter.isBiomeModification(operation)) return;

        var region = context.getRegion();
        if (region == null) return;

        var world = editSession.getWorld();

        weAdditions.getService(ChunkSyncService.class)
                .ifPresent(service -> service.sync(world, region.getChunks()));
    }
}
