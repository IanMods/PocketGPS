package club.iananderson.pocketgps.fabric.client;

import club.iananderson.pocketgps.fabric.event.InventoryEvent;
import net.fabricmc.api.ClientModInitializer;

public final class PocketGpsFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    InventoryEvent.register();
  }
}
