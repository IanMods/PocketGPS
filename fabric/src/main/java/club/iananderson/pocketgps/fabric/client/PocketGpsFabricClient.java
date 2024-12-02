package club.iananderson.pocketgps.fabric.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.fabric.event.InventoryEvent;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class PocketGpsFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ForgeConfigRegistry.INSTANCE.register(PocketGps.MOD_ID, Type.COMMON, PocketGpsConfig.GENERAL_SPEC,
                                          "pocketgps-common.toml");

    PocketGps.clientInit();
    FabricRegistration.itemInit();
    InventoryEvent.register();

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(FabricRegistration.POCKET_GPS);
    }
  }
}
