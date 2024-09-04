package club.iananderson.pocketgps.fabric;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import net.fabricmc.api.ModInitializer;

public final class PocketGpsFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    PocketGps.init();
    FabricRegistration.init();
    if (PocketGps.accessoriesLoaded()) {
      PocketGps.LOG.info("Talking to Accessories");
      AccessoriesCompat.init(FabricRegistration.POCKET_GPS);
    }
  }
}