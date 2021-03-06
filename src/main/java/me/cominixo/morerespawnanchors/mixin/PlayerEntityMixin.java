package me.cominixo.morerespawnanchors.mixin;

import me.cominixo.morerespawnanchors.block.BaseRespawnAnchor;
import me.cominixo.morerespawnanchors.block.EndRespawnAnchor;
import me.cominixo.morerespawnanchors.block.NetheriteRepawnAnchor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "findRespawnPosition", at = @At("HEAD"), cancellable = true)
    private static void addNewAnchors(ServerWorld world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3d>> cir) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof BaseRespawnAnchor) {
            BaseRespawnAnchor respawnAnchor = (BaseRespawnAnchor) block;
            if (blockState.get(respawnAnchor.getCharges()) > 0 && respawnAnchor.isDimension(world)) {
                Optional<Vec3d> respawnPosition = RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
                if (!bl2 && respawnPosition.isPresent()) {
                    world.setBlockState(pos, blockState.with(respawnAnchor.getCharges(), blockState.get(respawnAnchor.getCharges()) - 1), 3);
                }

                cir.setReturnValue(respawnPosition);
            }
        }
        }


}
