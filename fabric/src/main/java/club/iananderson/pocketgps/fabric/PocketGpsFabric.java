package club.iananderson.pocketgps.fabric;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class PocketGpsFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.

    // Run our common setup.
    PocketGps.init();
    ForgeConfigRegistry.INSTANCE.register(PocketGps.MOD_ID, Type.COMMON, PocketGpsConfig.GENERAL_SPEC,"pocketgps-common.toml");
  }
}
