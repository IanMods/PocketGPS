package club.iananderson.pocketgps.fabric;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import net.fabricmc.api.ModInitializer;

public final class PocketGpsFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    PocketGps.init();

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories");
      AccessoriesCompat.init(FabricRegistration.POCKET_GPS);
    }
  }
}
