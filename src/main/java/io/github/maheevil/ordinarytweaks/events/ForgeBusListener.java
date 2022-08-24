package io.github.maheevil.ordinarytweaks.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
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
}
