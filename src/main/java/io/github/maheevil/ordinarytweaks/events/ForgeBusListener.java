package io.github.maheevil.ordinarytweaks.events;

import io.github.maheevil.ordinarytweaks.SomeOrdinaryTweaksMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.ToastAddEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "ordinarytweaks",
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public class ForgeBusListener {
    /*@SubscribeEvent
    public static void renderGuiOverlayEvent(RenderGuiOverlayEvent.Pre overlayEvent){
        String langKey = overlayEvent.getOverlay().id().toShortLanguageKey();
        overlayEvent.setCanceled(
                langKey.equals("food_level")
                || langKey.equals("mount_health")
                || langKey.equals("jump_bar")
        );
    }*/

    @SubscribeEvent
    public static void toastAddEvent(ToastAddEvent toastAddEvent){
        switch(toastAddEvent.getToast().getClass().getSimpleName()){
            case "AdvancementToast" -> toastAddEvent.setCanceled(SomeOrdinaryTweaksMod.config.hideAdvancementToasts);
            case "RecipeToast" -> toastAddEvent.setCanceled(SomeOrdinaryTweaksMod.config.hideRecipeUnlockToasts);
            case "TutorialToast" -> toastAddEvent.setCanceled(SomeOrdinaryTweaksMod.config.hideTutorialToasts);
        }
    }

    @SubscribeEvent
    public static void renderHandEvent(RenderHandEvent renderHandEvent){
        boolean isOffHand = renderHandEvent.getHand() == InteractionHand.OFF_HAND;
        boolean willCancelForTotem =  isOffHand && SomeOrdinaryTweaksMod.config.invisibleTotem
                && renderHandEvent.getItemStack().getItem() == Items.TOTEM_OF_UNDYING;

        renderHandEvent.setCanceled(
               willCancelForTotem
        );

        if(willCancelForTotem) return;

        LocalPlayer player = Minecraft.getInstance().player;
        Item mainHandItem = player.getMainHandItem().getItem();

        renderHandEvent.setCanceled(
                SomeOrdinaryTweaksMod.config.invisibleShield
                        && isOffHand
                        && player.getOffhandItem().getItem() instanceof ShieldItem
                        && (
                                mainHandItem instanceof SwordItem
                                        || mainHandItem instanceof AxeItem
                                        || mainHandItem instanceof ProjectileWeaponItem
                                        || mainHandItem instanceof TridentItem
                        )
        );
    }
}
