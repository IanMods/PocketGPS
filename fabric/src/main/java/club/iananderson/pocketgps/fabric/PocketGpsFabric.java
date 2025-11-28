package club.iananderson.pocketgps.fabric;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig.Type;

public final class PocketGpsFabric implements ModInitializer {
  //Todo ---- Find out why it wont receive power on servers only

  @Override
  public void onInitialize() {
    PocketGps.init();
    FabricRegistration.register();

    NeoForgeConfigRegistry.INSTANCE.register(PocketGps.MOD_ID, Type.COMMON, PocketGpsConfig.GENERAL_SPEC,
                                             "pocketgps-common.toml");

    if (PocketGps.accessoriesLoaded() && !PocketGps.trinketsLoaded()) {
      PocketGps.LOG.info("Talking to Accessories");
      AccessoriesCompat.init(PocketGps.GPS.get());
    }
  }
}