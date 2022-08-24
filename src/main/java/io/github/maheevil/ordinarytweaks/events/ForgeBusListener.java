package io.github.maheevil.ordinarytweaks.events;

import io.github.maheevil.ordinarytweaks.SomeOrdinaryTweaksMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ToastAddEvent;
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
        String langKey = overlayEvent.getOverlay().id().toShortLanguageKey();
        overlayEvent.setCanceled(
                langKey.equals("food_level")
                || langKey.equals("mount_health")
                || langKey.equals("jump_bar")
        );
    }

    @SubscribeEvent
    public static void method(ToastAddEvent toastAddEvent){
        switch(toastAddEvent.getToast().getClass().getSimpleName()){
            case "AdvancementToast" -> toastAddEvent.setCanceled(SomeOrdinaryTweaksMod.config.hideAdvancementToasts);
            case "RecipeToast" -> toastAddEvent.setCanceled(SomeOrdinaryTweaksMod.config.hideRecipeUnlockToasts);
            case "TutorialToast" -> toastAddEvent.setCanceled(SomeOrdinaryTweaksMod.config.hideTutorialToasts);
        }
    }
}
