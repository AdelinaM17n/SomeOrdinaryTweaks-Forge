package io.github.maheevil.ordinarytweaks;

import io.github.maheevil.ordinarytweaks.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("ordinarytweaks")
public class SomeOrdinaryTweaksMod {

    public static final Logger LOGGER = LogManager.getLogger();
    public static ModConfig config;
    public SomeOrdinaryTweaksMod() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        ((minecraft, screen) -> AutoConfig.getConfigScreen(ModConfig.class,screen).get())
                )
        );
    }
}
