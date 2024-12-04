package club.iananderson.pocketgps.fabric.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.fabric.event.InventoryEvent;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.items.properties.GpsItemProperties;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class PocketGpsFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ForgeConfigRegistry.INSTANCE.register(PocketGps.MOD_ID, Type.COMMON, PocketGpsConfig.GENERAL_SPEC,
                                          "pocketgps-common.toml");

    PocketGps.clientInit();
    InventoryEvent.register();
    ItemProperties.register(FabricRegistration.POCKET_GPS, PocketGps.TOGGLE_GPS, new GpsItemProperties());

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(FabricRegistration.POCKET_GPS);
    }
  }
}
