package io.github.maheevil.ordinarytweaks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.maheevil.ordinarytweaks.SomeOrdinaryTweaksMod;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VanillaGuiOverlay.class)
public class VanillaGuiOverlayMixin {
    @Redirect(
            method = "lambda$static$10(Lnet/minecraftforge/client/gui/overlay/ForgeGui;Lcom/mojang/blaze3d/vertex/PoseStack;FII)V",
            at = @At(value = "INVOKE", target = "net/minecraft/client/player/LocalPlayer.getVehicle ()Lnet/minecraft/world/entity/Entity;",ordinal = 0)
    )
    private static Entity redirect$getVehicle(LocalPlayer localPlayer){
        return SomeOrdinaryTweaksMod.config.betterHorseHUD ? null : localPlayer.getVehicle();
    }

    @Inject(
            method = "lambda$static$13(Lnet/minecraftforge/client/gui/overlay/ForgeGui;Lcom/mojang/blaze3d/vertex/PoseStack;FII)V",
            at = @At(
                    value = "HEAD"
            ),
            remap = false
    )
    private static void handleLambdaInject(ForgeGui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight, CallbackInfo info){
        if(SomeOrdinaryTweaksMod.config.betterHorseHUD && gui.getMinecraft().options.keyJump.isDown() && gui.getMinecraft().player.isRidingJumpable()){
            gui.setupOverlayRenderState(true, false);
            gui.renderExperience(screenWidth / 2 - 91, poseStack);
            info.cancel();
        }
    }
}
