package io.github.maheevil.ordinarytweaks.events;

import io.github.maheevil.ordinarytweaks.SomeOrdinaryTweaksMod;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "ordinarytweaks",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public abstract class ModBusListener{

    @SubscribeEvent
    public static void guiEventHandler(RegisterGuiOverlaysEvent registerGuiOverlaysEvent){
        registerGuiOverlaysEvent.registerBelowAll("alternative_mount",(gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                gui.renderHealthMount(screenWidth, screenHeight, poseStack);
            }
        });

        registerGuiOverlaysEvent.registerBelowAll("alternative_food", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            boolean configMount = gui.getMinecraft().player.getVehicle() instanceof LivingEntity && !SomeOrdinaryTweaksMod.config.betterHorseHUD;
            if (!configMount && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                gui.renderFood(screenWidth, screenHeight, poseStack);
            }
        });
    }
}
