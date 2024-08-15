package club.iananderson.pocketgps.fabric;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.items.GpsItem;
import net.fabricmc.api.ModInitializer;

public final class PocketGpsFabric implements ModInitializer {
  public static final GpsItem POCKET_GPS = new GpsItem();

  @Override
  public void onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.

    // Run our common setup.
    PocketGps.init();
    FabricRegistration.init();
  }
}
