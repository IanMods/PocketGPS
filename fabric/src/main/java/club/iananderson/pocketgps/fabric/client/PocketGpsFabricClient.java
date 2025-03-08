package club.iananderson.pocketgps.fabric.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.items.properties.GpsItemProperties;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class PocketGpsFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ForgeConfigRegistry.INSTANCE.register(PocketGps.MOD_ID, Type.COMMON, PocketGpsConfig.GENERAL_SPEC,
                                          "pocketgps-common.toml");

    PocketGps.clientInit();
    ItemProperties.register(PocketGps.GPS.get(), PocketGps.TOGGLE_GPS, new GpsItemProperties());

    if (PocketGps.accessoriesLoaded() && !PocketGps.trinketsLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(PocketGps.GPS.get());
    }

    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (client.player != null) {
        PocketGpsClient.cachePlayerState(client.player);
      }
    });

    ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
      if (entity instanceof Player player) {
        PocketGpsClient.setInitializedMapState(false);
        PocketGpsClient.setIsDrawingMap(false);
        PocketGpsClient.cachePlayerState(player);
      }
    });

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(FabricRegistration.POCKET_GPS);
    }
  }
}
