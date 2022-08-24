package io.github.maheevil.ordinarytweaks.events;

import io.github.maheevil.ordinarytweaks.SomeOrdinaryTweaksMod;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "ordinarytweaks",
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public class ForgeBusListener {
    @SubscribeEvent
    public static void eventee(RenderGuiOverlayEvent.Pre overlayEvent){
        switch (overlayEvent.getOverlay().id().toShortLanguageKey()){
            case "food_level", "mount_health" -> overlayEvent.setCanceled(true);
            default -> {}
        }
    }

    @SubscribeEvent
    public static void renderBrightness(PlayerInteractEvent.RightClickBlock interactEvent){
        if (!interactEvent.getSide().isClient()) {
            System.out.println("Bruh");
            return;
        }
        Item itemInHand = interactEvent.getItemStack().getItem();

        if(itemInHand instanceof BlockItem blockItem){
            if(SomeOrdinaryTweaksMod.config.noDoubleSlabPlacement && blockItem.getBlock() instanceof SlabBlock){
                Level level = interactEvent.getLevel();
                Direction direction = interactEvent.getFace();
                boolean isHittingYAxis = direction.getAxis() == Direction.Axis.Y;
                BlockState blockState = isHittingYAxis
                        ? level.getBlockState(interactEvent.getPos())
                        : level.getBlockState(interactEvent.getPos().relative(direction));

                if( (blockState.getBlock() instanceof SlabBlock && blockState.getValue(SlabBlock.TYPE).equals(SlabType.BOTTOM))
                        || (isHittingYAxis && level.getBlockState(interactEvent.getPos().relative(direction)).getBlock() instanceof SlabBlock))
                {
                    interactEvent.setCancellationResult(InteractionResult.CONSUME);
                    interactEvent.setCanceled(true);
                }
            }

            Player localplayer = interactEvent.getEntity();
            if(SomeOrdinaryTweaksMod.config.doNotPlantEdiblesIfHungry
                    && localplayer.canEat(false)
                    && blockItem.isEdible()
                    && !localplayer.isSecondaryUseActive())
            {
                interactEvent.setUseItem(Event.Result.ALLOW);
                interactEvent.setUseBlock(Event.Result.DENY);
                interactEvent.setCanceled(true);
            }
        }
    }

    /*@SubscribeEvent
    public static void eee(ClientPlayerNetworkEvent){

    }*/
}
