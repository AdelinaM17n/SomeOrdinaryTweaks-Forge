package io.github.maheevil.ordinarytweaks.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "ordinarytweaks",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public abstract class ModBusListener extends ForgeGui {
    public ModBusListener(Minecraft mc) {
        super(mc);
    }

    @SubscribeEvent
    public static void guiEventHandler(RegisterGuiOverlaysEvent registerGuiOverlaysEvent){
        registerGuiOverlaysEvent.registerBelowAll("alternative_mount",(gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                gui.renderHealthMount(screenWidth, screenHeight, poseStack);
            }
        });

        registerGuiOverlaysEvent.registerBelowAll("alternative_food", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            boolean isMounted = false;// gui.getMinecraft().player.getVehicle() instanceof LivingEntity;
            if (!isMounted && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                gui.renderFood(screenWidth, screenHeight, poseStack);
            }
        });

    }
}
