package io.github.kg583.tradefair.block;

import io.github.kg583.tradefair.gui.LodestoneScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.stream.StreamSupport;

public class Lodestone extends Block {
    public Lodestone() {
        super(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LODESTONE).pistonBehavior(
                PistonBehavior.BLOCK));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            if (StreamSupport.stream(player.getHandItems().spliterator(), false).anyMatch(itemStack -> itemStack.isOf(Items.COMPASS))) {
                return ActionResult.PASS;
            }

            if (player instanceof ClientPlayerEntity) {
                ((ClientPlayerEntity)player).client.setScreen(new LodestoneScreen(this.getName()));
            }
        }
        return ActionResult.SUCCESS;
    }
}
