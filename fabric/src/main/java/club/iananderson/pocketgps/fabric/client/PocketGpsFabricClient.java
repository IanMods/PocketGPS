package club.iananderson.pocketgps.fabric.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.fabric.event.InventoryEvent;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import net.fabricmc.api.ClientModInitializer;

public final class PocketGpsFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    InventoryEvent.register();

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(FabricRegistration.POCKET_GPS);
    }
  }
}
