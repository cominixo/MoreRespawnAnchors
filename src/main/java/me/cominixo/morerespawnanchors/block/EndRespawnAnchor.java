package me.cominixo.morerespawnanchors.block;

import me.cominixo.morerespawnanchors.block.entity.EndRespawnAnchorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EndRespawnAnchor extends BaseRespawnAnchor implements BlockEntityProvider {
    public EndRespawnAnchor(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(getCharges(), 0));
    }

    @Override
    protected boolean isChargeItem(ItemStack stack) {
        return stack.getItem() == Items.ENDER_PEARL;
    }

    @Override
    public boolean isDimension(World world) {
        return world.getRegistryKey().equals(World.END);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(getCharges());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new EndRespawnAnchorBlockEntity(getCharges());
    }

}
